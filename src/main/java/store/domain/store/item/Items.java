package store.domain.store.item;

import static store.common.ErrorMessage.INVALID_INPUT;

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

    public int checkRemainingPromotionStock(final String name) {
        Item item = findPromotionItemByName(name);
        return item.getQuantity();
    }

    public int getPromotionBundleSize(final String name, final Promotions promotions) {
        Item item = findPromotionItemByName(name);
        String promotionName = item.getPromotionName();
        Promotion promotion = findPromotionByName(promotionName, promotions);
        return promotion.getPromotionBundleSize();
    }

    private Item findPromotionItemByName(final String name) {
        return items.stream()
                .filter(item -> item.findPromotionItemByName(name))
                .findFirst()
                .orElseThrow(() -> new AppException(INVALID_INPUT.getMessage()));
    }

    private Promotion findPromotionByName(final String name, final Promotions promotions) {
        return promotions.findPromotionByName(name);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
