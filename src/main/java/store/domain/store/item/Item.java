package store.domain.store.item;

public class Item {

    private static final String NO_PROMOTION = "";
    private final String name;
    private final int price;
    private final String promotionName;
    private int quantity;

    public Item(String name, int price, int quantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
    }

    public boolean findPromotionItemByName(final String name) {
        return this.name.equals(name) && isPromotionItem();
    }

    boolean isEqual(final String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotionName() {
        return promotionName;
    }

    private boolean isPromotionItem() {
        return !(this.promotionName.equals(NO_PROMOTION));
    }
}
