package store.domain.store.item;

import java.util.Collections;
import java.util.List;

public class Items {

    private final List<Item> items;

    public Items(final List<Item> items) {
        this.items = items;
    }

    public boolean isNotContain(final String name) {
        return items.stream()
                .noneMatch(item -> item.isEqual(name));
    }

    public int checkRemainingStock(final String name) {
        return items.stream()
                .filter(item -> item.isEqual(name))
                .mapToInt(Item::getQuantity)
                .sum();
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    /*
    public Item findItemByName(final String name) {
        return items.stream()
                .filter(item -> item.isEqual(name))
                .findFirst()
                .orElseThrow(() -> new AppException(NOT_FOUND.getMessage()));
    }

    public int checkRemainingPromotionStock(final String name) {
        return findPromotionItemByName(name)
                .map(Item::getQuantity)
                .orElseThrow(() -> new AppException(NOT_FOUND.getMessage()));
    }

    public int getPromotionBundleSize(final String name, final Promotions promotions) {
        return findPromotionItemByName(name)
                .map(item -> findPromotionByName(item.getPromotionName(), promotions))
                .map(Promotion::getPromotionBundleSize)
                .orElseThrow(() -> new AppException(NOT_FOUND.getMessage()));
    }

    public boolean isExistPromotionProduct(final String name) {
        Optional<Item> item = findPromotionItemByName(name);
        return item.isPresent();
    }

    public String findPromotionNameOfItem(final String name) {
        Item item = findPromotionItemByName(name)
                .orElseThrow(() -> new AppException(NOT_FOUND.getMessage()));
        return item.getPromotionName();
    }



    private Optional<Item> findPromotionItemByName(final String name) {
        return items.stream()
                .filter(item -> item.findPromotionItemByName(name))
                .findFirst();
    }

    private Promotion findPromotionByName(final String name, final Promotions promotions) {
        return promotions.findPromotionByName(name);
    }
    */
}
