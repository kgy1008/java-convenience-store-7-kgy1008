package store.domain.store.item;

import store.domain.store.promotion.DiscountPolicy;
import store.domain.store.promotion.Promotion;

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

    boolean isEqual(final String name) {
        return this.name.equals(name);
    }

    boolean findPromotionItemByName(final String name) {
        return this.name.equals(name) && isPromotionItem();
    }

    private boolean isPromotionItem() {
        return this.discountPolicy instanceof Promotion;
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

    public int getPromotionBundleSize() {
        return discountPolicy.getPromotionBundleSize();
    }
}
