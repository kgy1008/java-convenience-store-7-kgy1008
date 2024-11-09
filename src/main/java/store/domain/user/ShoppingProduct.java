package store.domain.user;

import java.time.LocalDate;
import java.util.Objects;

public class ShoppingProduct {

    private final String name;
    private final int price;
    private int quantity;
    private final LocalDate date;

    public ShoppingProduct(final String name, final int price, final int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.date = LocalDate.of(2024, 2, 1);
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

    public int getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
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
        return price == that.price && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
