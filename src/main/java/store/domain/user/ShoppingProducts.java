package store.domain.user;

import java.util.Collections;
import java.util.List;

public class ShoppingProducts {

    private final List<ShoppingProduct> products;

    public ShoppingProducts(final List<ShoppingProduct> products) {
        this.products = products;
    }

    public List<ShoppingProduct> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
