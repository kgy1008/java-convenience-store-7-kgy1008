package store.domain.user;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final List<ShoppingProduct> shoppingProducts;

    public Customer() {
        this.shoppingProducts = new ArrayList<>();
    }

    public void purchase(List<ShoppingProduct> shoppingProducts) {
        this.shoppingProducts.addAll(shoppingProducts);
    }
}
