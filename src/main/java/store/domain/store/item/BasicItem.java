package store.domain.store.item;

public class BasicItem implements Item {

    private static final String NO_PROMOTION = "";

    private final String name;
    private final int price;
    private final String promotionName;
    private int quantity;

    public BasicItem(final String name, final int price, final int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = NO_PROMOTION;
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
}
