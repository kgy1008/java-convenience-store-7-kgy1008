package store.domain.user;

import java.util.Objects;

public class ShoppingProduct {

    private final String name;
    private int quantity;

    public ShoppingProduct(final String name, final int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    void decreaseQuantity(int count) {
        this.quantity -= count;
    }

    void increaseQuantity(int count) {
        this.quantity += count;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingProduct that = (ShoppingProduct) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
