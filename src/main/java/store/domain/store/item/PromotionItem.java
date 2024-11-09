package store.domain.store.item;

public class PromotionItem implements Item {

    private final String name;
    private final int price;
    private final String promotionName;
    private int quantity;

    public PromotionItem(final String name, final int price, final int quantity, final String promotionName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
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

    @Override
    public boolean isEqual(final String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean isPromotionProduct() {
        return true;
    }
}
