package store.domain.store;

public class CalculableItem {

    private final String name;
    private final int quantity;
    private final int price;

    public CalculableItem(final String name, final int quantity, final int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
