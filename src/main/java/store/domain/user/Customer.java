package store.domain.user;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private static final int FREE_BENEFIT = 1;
    private final List<ShoppingProduct> cart;

    public Customer() {
        this.cart = new ArrayList<>();
    }

    public void purchase(List<ShoppingProduct> shoppingProducts) {
        this.cart.addAll(shoppingProducts);
    }

    public void removeFromCart(final ShoppingProduct shoppingProduct, final int count) {
        shoppingProduct.decreaseQuantity(count);
    }

    public void addCart(final ShoppingProduct shoppingProduct) {
        shoppingProduct.increaseQuantity(FREE_BENEFIT);
    }
}
