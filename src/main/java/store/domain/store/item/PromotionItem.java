package store.domain.store.item;

import static store.common.ErrorMessage.INVALID_RANGE;

import store.common.exception.AppException;

public class PromotionItem implements Item {

    private static final int EMPTY_STOCK = 0;

    private final String name;
    private final int price;
    private final String promotionName;
    private int quantity;

    public PromotionItem(final String name, final int price, final int quantity, final String promotionName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
        validateQuantity();
    }

    private void validateQuantity() {
        if (quantity < EMPTY_STOCK) {
            throw new AppException(INVALID_RANGE.getMessage());
        }
    }

    @Override
    public void decreaseQuantity(final int count) {
        quantity -= count;
        validateQuantity();
    }

    @Override
    public void increaseQuantity(final int count) {
        quantity += count;
    }

    @Override
    public boolean isEqual(final String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean isPromotionItem() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getPromotionName() {
        return promotionName;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
