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
    private static final String NO_PROMOTION = "null";
    private static final String NO_PROMOTION_NAME = "";
    private static final int EMPTY = 0;

    public Items getItems() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(PRODUCT_FILE_PATH));
            return new Items(readFileLines(lines));
        } catch (IOException e) {
            throw new FileReadException(CAN_NOT_READ.getMessage(), e);
        }
    }

    private List<Item> readFileLines(final List<String> lines) {
        List<Item> items = new ArrayList<>();
        Optional<Item> previousItem = Optional.empty();
        for (String line : lines.stream().skip(HEADER_LINE).toList()) {
            previousItem = convertToItem(line, items, previousItem);
        }
        addBaseItemForLastItemWhenMissing(items, previousItem);
        return items;
    }

    private Optional<Item> convertToItem(final String line, final List<Item> items, final Optional<Item> previousItem) {
        Item currentItem = parseData(line);
        if (previousItem.isPresent() && isBaseItemMissing(previousItem.get(), currentItem)) {
            addEmptyBaseItem(items, previousItem.get());
        }
        items.add(currentItem);
        return Optional.of(currentItem);
    }

    private Item parseData(final String line) {
        String[] values = line.split(DELIMITER);
        String name = values[0];
        int price = convertStringToInt(values[1]);
        int quantity = convertStringToInt(values[2]);
        String promotionName = setPromotionName(values[3]);
        if (promotionName.equals(NO_PROMOTION_NAME)) {
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
        if (NO_PROMOTION.equals(name)) {
            return NO_PROMOTION_NAME;
        }
        return name;
    }

    private boolean isBaseItemMissing(final Item previousItem, final Item currentItem) {
        return isItemNameChanged(previousItem, currentItem) && isPromotionalItem(previousItem);
    }

    private boolean isItemNameChanged(final Item previousItem, final Item currentItem) {
        return !previousItem.getName().equals(currentItem.getName());
    }

    private boolean isPromotionalItem(final Item item) {
        return !item.getPromotionName().equals(NO_PROMOTION_NAME);
    }

    private void addEmptyBaseItem(final List<Item> items, final Item item) {
        items.add(createEmptyBaseItem(item));
    }

    private Item createEmptyBaseItem(final Item promotionalItem) {
        return new BasicItem(promotionalItem.getName(), promotionalItem.getPrice(), EMPTY);
    }

    private void addBaseItemForLastItemWhenMissing(final List<Item> items, final Optional<Item> previousItem) {
        if (previousItem.isPresent() && isPromotionalItem(previousItem.get())) {
            items.add(createEmptyBaseItem(previousItem.get()));
        }
    }
}
