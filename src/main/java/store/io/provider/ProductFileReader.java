package store.io.provider;

import static store.common.ErrorMessage.CAN_NOT_READ;
import static store.common.ErrorMessage.INVALID_FORMAT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.common.exception.FileReadException;
import store.domain.store.item.BasicItem;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;

public class ProductFileReader {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final String DELIMITER = ",";
    private static final int HEADER_LINE = 1;
    private static final String NO_PROMOTION = "";
    private static final int EMPTY = 0;

    public Items getItems() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(PRODUCT_FILE_PATH));
            return new Items(processLines(lines));
        } catch (IOException e) {
            throw new FileReadException(CAN_NOT_READ.getMessage(), e);
        }
    }

    private List<Item> processLines(final List<String> lines) {
        List<Item> items = new ArrayList<>();
        Optional<Item> previousItem = Optional.empty();
        for (String line : lines.stream().skip(HEADER_LINE).toList()) {
            previousItem = processLine(line, items, previousItem);
        }
        addBaseItemForLastItem(items, previousItem);
        return items;
    }

    private Optional<Item> processLine(final String line, final List<Item> items, final Optional<Item> previousItem) {
        Item currentItem = parseData(line);
        if (previousItem.isPresent() && shouldAddBaseItem(previousItem.get(), currentItem)) {
            addBaseItemIfMissing(items, previousItem.get());
        }
        items.add(currentItem);
        return Optional.of(currentItem);
    }

    private void addBaseItemForLastItem(final List<Item> items, final Optional<Item> previousItem) {
        if (previousItem.isPresent() && isPromotionalItem(previousItem.get()) && isBaseItemMissing(items,
                previousItem.get())) {
            items.add(createEmptyBaseItem(previousItem.get()));
        }
    }

    private boolean shouldAddBaseItem(final Item previousItem, final Item currentItem) {
        return !previousItem.getName().equals(currentItem.getName());
    }

    private void addBaseItemIfMissing(final List<Item> items, final Item item) {
        if (isPromotionalItem(item) && isBaseItemMissing(items, item)) {
            items.add(createEmptyBaseItem(item));
        }
    }

    private Item parseData(final String line) {
        String[] values = line.split(DELIMITER);
        String name = values[0];
        int price = convertStringToInt(values[1]);
        int quantity = convertStringToInt(values[2]);
        String promotionName = setPromotionName(values[3]);
        if (promotionName.equals(NO_PROMOTION)) {
            return new BasicItem(name, price, quantity);
        }
        return new PromotionItem(name, price, quantity, promotionName);
    }

    private int convertStringToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new FileReadException(INVALID_FORMAT.getMessage(), e);
        }
    }

    private String setPromotionName(final String name) {
        if ("null".equals(name)) {
            return NO_PROMOTION;
        }
        return name;
    }

    private boolean isPromotionalItem(final Item item) {
        return !item.getPromotionName().equals(NO_PROMOTION);
    }

    private boolean isBaseItemMissing(final List<Item> items, final Item promotionalItem) {
        return items.stream()
                .noneMatch(existingItem -> existingItem.getName().equals(promotionalItem.getName()) &&
                        existingItem.getPromotionName().equals(NO_PROMOTION));
    }

    private Item createEmptyBaseItem(final Item promotionalItem) {
        return new BasicItem(promotionalItem.getName(), promotionalItem.getPrice(), EMPTY);
    }
}
