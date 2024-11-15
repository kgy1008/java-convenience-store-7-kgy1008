package store.domain.store.item;

import static store.common.ErrorMessage.NOT_FOUND;

import java.util.Collections;
import java.util.List;
import store.common.exception.AppException;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.Promotions;

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

    public boolean isPromotionItem(final String name) {
        Item item = findItemByName(name);
        return item.isPromotionItem();
    }

    public Item findItemByName(final String name) {
        return items.stream()
                .filter(item -> item.isEqual(name))
                .findFirst()
                .orElseThrow(() -> new AppException(NOT_FOUND.getMessage()));
    }

    public List<Item> findItemsByName(final String name) {
        return items.stream()
                .filter(item -> item.isEqual(name))
                .toList();
    }

    public int checkRemainingPromotionStock(final String name) {
        Item promotionItem = findItemByName(name);
        return promotionItem.getQuantity();
    }

    public int getPromotionBundleSize(final String name, final Promotions promotions) {
        Item promotionItem = findItemByName(name);
        Promotion promotion = promotions.findPromotionByName(promotionItem.getPromotionName());
        return promotion.getPromotionBundleSize();
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
