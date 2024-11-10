package store.domain.store.item;

public interface Item {

    void decreaseQuantity(final int quantity);

    void increaseQuantity(final int count);

    boolean isEqual(final String name);

    boolean isPromotionProduct();

    String getName();

    int getPrice();

    String getPromotionName();

    int getQuantity();
}
