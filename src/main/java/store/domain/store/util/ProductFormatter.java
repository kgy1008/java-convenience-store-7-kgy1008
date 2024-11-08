package store.domain.store.util;

import static store.common.ErrorMessage.EXCEED_QUANTITY;
import static store.common.ErrorMessage.NOT_FOUND;

import java.util.Arrays;
import java.util.List;
import store.common.exception.AppException;
import store.domain.store.item.Items;
import store.domain.user.ShoppingProduct;

public class ProductFormatter {

    private static final String DELIMITER = ",";
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";
    private static final String DETAIL_DELIMITER = "-";

    public List<ShoppingProduct> convertStringToItem(final String input, final Items items) {
        return Arrays.stream(input.split(DELIMITER))
                .map(item -> parseData(item, items))
                .toList();
    }

    private ShoppingProduct parseData(final String input, final Items items) {
        String[] data = input.replace(PREFIX, "").replace(SUFFIX, "").split(DETAIL_DELIMITER);
        String name = data[0];
        int quantity = Integer.parseInt(data[1]);
        validate(name, quantity, items);
        return new ShoppingProduct(name, quantity);
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
        if (items.checkRemainingStock(name) < quantity) {
            throw new AppException(EXCEED_QUANTITY.getMessage());
        }
    }
}
