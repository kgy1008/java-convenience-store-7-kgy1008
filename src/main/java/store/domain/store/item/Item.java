package store.domain.store.item;

public interface Item {

    String getName();

    int getPrice();

    String getPromotionName();

    int getQuantity();

    boolean isEqual(final String name);

    boolean isPromotionProduct();

}
