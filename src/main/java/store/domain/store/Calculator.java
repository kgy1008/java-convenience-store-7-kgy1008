package store.domain.store;

import java.util.ArrayList;
import java.util.List;
import store.domain.user.ShoppingProduct;
import store.dto.Gift;
import store.dto.Receipt;

public class Calculator {

    private static final int NO_MEMBERSHIP_DISCOUNT = 0;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private final List<ShoppingProduct> cart;
    private final List<Gift> gifts;

    public Calculator(final List<ShoppingProduct> products, final List<Gift> gifts) {
        this.cart = new ArrayList<>(products);
        this.gifts = new ArrayList<>(gifts);
    }

    public Receipt calculatePrice(final boolean hasMembershipBenefit) {
        int totalPrice = calculateTotalPrice();
        int promotionDiscountPrice = calculatePromotionDiscountPrice();
        int memberShipDiscountPrice = calculateMemberShipDiscountPrice(hasMembershipBenefit, totalPrice);
        int payment = totalPrice - (promotionDiscountPrice + memberShipDiscountPrice);
        return Receipt.generate(cart, gifts, totalPrice, promotionDiscountPrice, memberShipDiscountPrice, payment);
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

    private int calculateMemberShipDiscountPrice(final boolean hasMembershipBenefit, final int totalPrice) {
        if (hasMembershipBenefit) {
            int targetPrice = calculateTargetPrice(totalPrice);
            int finalPrice = (int) (targetPrice * MEMBERSHIP_DISCOUNT_RATE);
            return Math.min(MAX_MEMBERSHIP_DISCOUNT, finalPrice);
        }
        return NO_MEMBERSHIP_DISCOUNT;
    }

    private int calculateTargetPrice(final int totalPrice) {
        int noMemberShipDiscountTargetPrice = gifts.stream()
                .mapToInt(gift -> gift.quantity() * gift.price() * gift.promotionGroupSize())
                .sum();
        return totalPrice - noMemberShipDiscountTargetPrice;
    }
}
