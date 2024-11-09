package store.domain.store;

import java.util.ArrayList;
import java.util.List;
import store.domain.user.ShoppingProduct;

public class Calculator {

    private final List<ShoppingProduct> cart;

    public Calculator(final List<ShoppingProduct> shoppingProducts) {
        cart = new ArrayList<>(shoppingProducts);
    }
}
