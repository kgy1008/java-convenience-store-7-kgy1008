package store.domain.store.item;

public interface Item {

    String getName();

    int getPrice();

    String getPromotionName();

    int getQuantity();

    boolean isEqual(String name);

    boolean isPromotionProduct();

}
