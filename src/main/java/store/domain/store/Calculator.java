package store.domain.store;

import java.util.ArrayList;
import java.util.List;
import store.domain.user.ShoppingProduct;
import store.dto.Gift;
import store.dto.Receipt;

public class Calculator {

    private static final int NO_MEMBERSHIP_DISCOUNT = 0;
    private final List<ShoppingProduct> cart;
    private final List<Gift> gifts;

    public Calculator(final List<ShoppingProduct> products, final List<Gift> gifts) {
        this.cart = new ArrayList<>(products);
        this.gifts = new ArrayList<>(gifts);
    }

    public Receipt calculatePrice(final boolean hasMembershipBenefit) {
        int totalPrice = calculateTotalPrice();
        int promotionDiscountPrice = calculatePromotionDiscountPrice();

    }

    private int calculateTotalPrice() {
        return cart.stream()
                .mapToInt(product ->
                        product.getQuantity() * product.getPrice()
                )
                .sum();
    }

    private int calculatePromotionDiscountPrice() {
        return gifts.stream()
                .mapToInt(gift ->
                        gift.quantity() * gift.price()
                )
                .sum();
    }


}
