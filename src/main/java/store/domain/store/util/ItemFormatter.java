package store.domain.store.util;


import static store.common.ErrorMessage.CONFLICT_EXCEPTION;
import static store.common.ErrorMessage.EXCEED_QUANTITY;
import static store.common.ErrorMessage.INVALID_FORMAT;
import static store.common.ErrorMessage.INVALID_INPUT;
import static store.common.ErrorMessage.NOT_FOUND;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import store.common.exception.AppException;
import store.domain.store.item.Items;
import store.domain.user.ShoppingItem;

public class ItemFormatter {

    private static final String DELIMITER = ",";
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";
    private static final String DETAIL_DELIMITER = "-";
    private static final int MINIMUM_PURCHASE_QUANTITY = 1;

    public List<ShoppingItem> convertStringToItem(final String input, final Items items) {
        List<ShoppingItem> shoppingItems = Arrays.stream(input.split(DELIMITER))
                .map(item -> parseData(item, items))
                .toList();
        validateNoDuplicate(shoppingItems);
        return shoppingItems;
    }

    private ShoppingItem parseData(final String input, final Items items) {
        String[] data = input.replace(PREFIX, "").replace(SUFFIX, "").trim().split(DETAIL_DELIMITER);
        String name = data[0];
        int quantity = convertStringToInt(data[1]);
        validate(name, quantity, items);
        return new ShoppingItem(name, quantity);
    }

    private int convertStringToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new AppException(INVALID_FORMAT.getMessage());
        }
    }

    private void validate(final String name, final int quantity, final Items items) {
        validateName(name, items);
        validateQuantity(name, quantity, items);
    }

    private void validateName(final String name, final Items items) {
        if (items.isNotContain(name)) {
            throw new AppException(NOT_FOUND.getMessage());
        }
    }

    private void validateQuantity(final String name, final int quantity, final Items items) {
        if (quantity < MINIMUM_PURCHASE_QUANTITY) {
            throw new AppException(INVALID_INPUT.getMessage());
        }
        if (isExceedQuantity(name, quantity, items)) {
            throw new AppException(EXCEED_QUANTITY.getMessage());
        }
    }

    private boolean isExceedQuantity(final String name, final int quantity, final Items items) {
        return items.checkRemainingStock(name) < quantity;
    }

    private void validateNoDuplicate(final List<ShoppingItem> shoppingItems) {
        Set<ShoppingItem> ItemSet = new HashSet<>(shoppingItems);
        if (ItemSet.size() != shoppingItems.size()) {
            throw new AppException(CONFLICT_EXCEPTION.getMessage());
        }
    }
}

