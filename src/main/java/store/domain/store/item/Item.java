package store.domain.store.item;

import store.domain.store.promotion.DiscountPolicy;

public class Item {
    private final String name;
    private final int price;
    private final DiscountPolicy discountPolicy;
    private int quantity;

    public Item(String name, int price, int quantity, DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discountPolicy = discountPolicy;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getPromotionName() {
        return discountPolicy.getPolicyName();
    }

    public int getQuantity() {
        return quantity;
    }
}
