package store.domain.store.item;

import static store.common.ErrorMessage.NOT_FOUND;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public Optional<Item> findPromotionItemByName(final String name) {
        return items.stream()
                .filter(item -> item.findPromotionItemByName(name))
                .findFirst();
    }

    private Promotion findPromotionByName(final String name, final Promotions promotions) {
        return promotions.findPromotionByName(name);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
