package store.io.provider;

import static store.common.ErrorMessage.CAN_NOT_READ;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            List<String> lines = Files.readAllLines(Paths.get(PRODUCT_FILE_PATH));
            List<Item> items = lines.stream()
                    .skip(HEADER_LINE)
                    .map(this::parseItem)
                    .toList();
            return new Items(items);
        } catch (IOException e) {
            throw new FileReadException(CAN_NOT_READ.getMessage(), e);
        }
    }

    private Item parseItem(final String line) {
        String[] values = line.split(DELIMITER);
        String name = values[0];
        int price = Integer.parseInt(values[1]);
        int quantity = Integer.parseInt(values[2]);
        String promotionName = setPromotionName(values[3]);
        return new Item(name, price, quantity, promotionName);
    }

    private String setPromotionName(final String name) {
        if ("null".equals(name)) {
            return NO_PROMOTION;
        }
        return name;
    }
}
