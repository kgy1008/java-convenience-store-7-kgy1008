package store.domain.store;

import java.util.List;
import store.domain.store.config.Initializer;
import store.domain.store.item.BasicItem;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.Promotions;
import store.domain.store.util.ProductFormatter;
import store.domain.user.ShoppingProduct;
import store.domain.user.ShoppingProducts;

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

    public ShoppingProducts getShoppingItemsFromUser(final String input) {
        ProductFormatter productFormatter = new ProductFormatter();
        List<ShoppingProduct> products = productFormatter.convertStringToItem(input, items);
        return new ShoppingProducts(products);
    }

    boolean isPromotionApplicableToday(final ShoppingProduct shoppingProduct) {
        if (items.isExistPromotionProduct(shoppingProduct.getName())) {
            String promotionName = items.getPromotionNameOfItem(shoppingProduct.getName());
            Promotion promotion = promotions.findPromotionByName(promotionName);
            return promotion.isBetweenPromotionDuration();
        }
        return false;
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

    public int calculateItemCountWithoutPromotion(final PromotionItem promotionItem) {
        int inputQuantity = promotionItem.getQuantity();
        int promotionItemsAvailableSize = calculateMaxCountOfPromotionApplied(promotionItem);
        return inputQuantity - promotionItemsAvailableSize;
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
