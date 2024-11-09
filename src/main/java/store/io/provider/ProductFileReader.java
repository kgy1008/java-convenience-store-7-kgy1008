package store.io.provider;

import static store.common.ErrorMessage.CAN_NOT_READ;
import static store.common.ErrorMessage.INVALID_FORMAT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import store.common.exception.FileReadException;
import store.domain.store.item.Item;
import store.domain.store.item.Items;

public class ProductFileReader {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final String DELIMITER = ",";
    private static final int HEADER_LINE = 1;
    private static final String NO_PROMOTION = "";

    public Items getItems() {
        try {
            List<Item> allItems = readAllItems();
            List<Item> itemsWithBaseItems = addMissingBaseItems(allItems);
            return new Items(itemsWithBaseItems);
        } catch (IOException e) {
            throw new FileReadException(CAN_NOT_READ.getMessage(), e);
        }
    }

    private List<Item> readAllItems() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(PRODUCT_FILE_PATH));
        List<Item> items = new ArrayList<>();

        for (String line : lines.stream().skip(HEADER_LINE).toList()) {
            items.add(parseItem(line));
        }
        return items;
    }

    private Item parseItem(final String line) {
        String[] values = line.split(DELIMITER);
        String name = values[0];
        int price = convertStringToInt(values[1]);
        int quantity = convertStringToInt(values[2]);
        String promotionName = setPromotionName(values[3]);
        return new Item(name, price, quantity, promotionName);
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

    private List<Item> addMissingBaseItems(List<Item> allItems) {
        List<Item> itemsWithBaseItems = new ArrayList<>(allItems);

        for (Item item : allItems) {
            if (isPromotionalItem(item) && !hasBaseItem(allItems, item)) {
                itemsWithBaseItems.add(createBaseItem(item));
            }
        }

        return itemsWithBaseItems;
    }

    private boolean isPromotionalItem(Item item) {
        return !item.getPromotionName().equals(NO_PROMOTION);
    }

    private boolean hasBaseItem(List<Item> allItems, Item promotionalItem) {
        return allItems.stream()
                .anyMatch(existingItem -> existingItem.getName().equals(promotionalItem.getName()) &&
                        existingItem.getPromotionName().equals(NO_PROMOTION));
    }

    private Item createBaseItem(Item promotionalItem) {
        return new Item(promotionalItem.getName(), promotionalItem.getPrice(), 0, NO_PROMOTION);
    }
}
