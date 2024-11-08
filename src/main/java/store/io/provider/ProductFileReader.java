package store.io.provider;

import static store.common.ErrorMessage.CAN_NOT_READ;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import store.common.exception.FileReadException;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.promotion.DiscountPolicy;
import store.domain.store.promotion.NoneDiscountPolicy;
import store.domain.store.promotion.Promotions;

public class ProductFileReader {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final String DELIMITER = ",";
    private static final int HEADER_LINE = 1;

    public Items getItems(final Promotions promotions) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(PRODUCT_FILE_PATH));
            List<Item> items = lines.stream()
                    .skip(HEADER_LINE)
                    .map(line -> parseItem(line, promotions))
                    .toList();
            return new Items(items);
        } catch (IOException e) {
            throw new FileReadException(CAN_NOT_READ.getMessage(), e);
        }
    }

    private Item parseItem(final String line, final Promotions promotions) {
        String[] values = line.split(DELIMITER);
        String name = values[0];
        int price = Integer.parseInt(values[1]);
        int quantity = Integer.parseInt(values[2]);
        DiscountPolicy discountPolicy = setDiscountPolicy(values[3], promotions);
        return new Item(name, price, quantity, discountPolicy);
    }

    private DiscountPolicy setDiscountPolicy(final String promotionName, final Promotions promotions) {
        if (promotions.isNotContain(promotionName)) {
            return new NoneDiscountPolicy();
        }
        return promotions.findPromotionByName(promotionName);
    }
}
