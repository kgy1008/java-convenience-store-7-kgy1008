package store.domain.store;

import java.util.List;
import store.domain.store.config.Initializer;
import store.domain.store.item.BasicItem;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.Promotions;
import store.domain.store.util.ItemFormatter;
import store.domain.user.ShoppingItem;
import store.domain.user.ShoppingItems;

public class Convenience {

    private static final int EXACT_MATCH = 0;

    private final Initializer initializer;
    private final Promotions promotions;
    private final Items items;

    public Convenience() {
        this.initializer = new Initializer();
        this.promotions = initializer.initPromotions();
        this.items = initializer.initItems();
    }

    public ShoppingItems getShoppingItemsFromUser(final String input) {
        ItemFormatter itemFormatter = new ItemFormatter();
        List<ShoppingItem> shoppingItems = itemFormatter.convertStringToItem(input, items);
        return new ShoppingItems(shoppingItems);
    }

    public int calculateItemCountWithoutPromotion(final PromotionItem promotionItem) {
        int inputQuantity = promotionItem.getQuantity();
        int promotionItemsAvailableSize = calculateMaxCountOfPromotionApplied(promotionItem);
        return inputQuantity - promotionItemsAvailableSize;
    }

    boolean isPromotionNotApplicableToAllItems(final PromotionItem promotionItem) {
        return calculateMaxCountOfPromotionApplied(promotionItem) < promotionItem.getQuantity();
    }

    private int calculateMaxCountOfPromotionApplied(final PromotionItem promotionItem) {
        int remainingStock = items.checkRemainingPromotionStock(promotionItem.getName());
        int promotionGroupSize = items.getPromotionBundleSize(promotionItem.getName(), promotions);
        int fullPromotionGroupCount = remainingStock / promotionGroupSize;
        return fullPromotionGroupCount * promotionGroupSize;
    }

    boolean isPromotionApplicableToday(final ShoppingItem shoppingItem) {
        if (items.isPromotionItem(shoppingItem.getName())) {
            Item item = items.findItemByName(shoppingItem.getName());
            String promotionName = item.getPromotionName();
            Promotion promotion = promotions.findPromotionByName(promotionName);
            return promotion.isBetweenPromotionDuration();
        }
        return false;
    }

    boolean canReceiveAdditionalBenefit(final PromotionItem promotionItem) {
        return isLessThanPromotionRemainingStock(promotionItem) && isLessThanPromotionStandard(promotionItem);
    }

    private boolean isLessThanPromotionRemainingStock(final PromotionItem promotionItem) {
        return items.checkRemainingPromotionStock(promotionItem.getName()) > promotionItem.getQuantity();
    }

    private boolean isLessThanPromotionStandard(final PromotionItem promotionItem) {
        int promotionGroupSize = items.getPromotionBundleSize(promotionItem.getName(), promotions);
        return (promotionItem.getQuantity() % promotionGroupSize) != EXACT_MATCH;
    }

    int calculateNumberOfFreeItem(final PromotionItem promotionItem) {
        int promotionItemsAvailableSize = calculateMaxCountOfPromotionApplied(promotionItem);
        int promotionGroupSize = items.getPromotionBundleSize(promotionItem.getName(), promotions);
        int maxStockBasedPromotionApplied = promotionItemsAvailableSize / promotionGroupSize;

        int inputPromotionAppliedCount = promotionItem.getQuantity() / promotionGroupSize;

        return Math.min(maxStockBasedPromotionApplied, inputPromotionAppliedCount);
    }

    int findPromotionBundleSize(final PromotionItem promotionItem) {
        return items.getPromotionBundleSize(promotionItem.getName(), promotions);
    }

    void updateItemQuantity(final List<PromotionItem> soldPromotionItems, final List<BasicItem> soldBasicItems) {
        updatePromotionItemQuantity(soldPromotionItems);
        updateBasicItemQuantity(soldBasicItems);
    }

    private void updatePromotionItemQuantity(final List<PromotionItem> soldPromotionItems) {
        for (PromotionItem soldPromotionItem : soldPromotionItems) {
            List<Item> items = this.items.findItemsByName(soldPromotionItem.getName());
            Item promotionItem = items.getFirst();
            if (isPromotionStockInsufficient(promotionItem, soldPromotionItem)) {
                handleInsufficientQuantity(promotionItem, items.getLast(), soldPromotionItem.getQuantity());
                continue;
            }
            promotionItem.decreaseQuantity(soldPromotionItem.getQuantity());
        }
    }

    private boolean isPromotionStockInsufficient(final Item promotionItem, final PromotionItem soldPromotionItem) {
        return promotionItem.getQuantity() < soldPromotionItem.getQuantity();
    }

    private void handleInsufficientQuantity(final Item promotionItem, final Item basicItem, final int soldQuantity) {
        int remainSoldQuantity = soldQuantity - promotionItem.getQuantity();
        promotionItem.decreaseQuantity(promotionItem.getQuantity());
        basicItem.decreaseQuantity(remainSoldQuantity);
    }

    private void updateBasicItemQuantity(final List<BasicItem> soldBasicItems) {
        for (BasicItem soldBasicItem : soldBasicItems) {
            Item basicItem = items.findItemByName(soldBasicItem.getName());
            basicItem.decreaseQuantity(soldBasicItem.getQuantity());
        }
    }

    public Items getItems() {
        return items;
    }
}
