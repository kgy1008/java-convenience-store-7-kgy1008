package store.domain.store;

import java.util.List;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;
import store.domain.store.util.ProductFormatter;
import store.domain.user.ShoppingProduct;

public class Convenience {

    private static final int EXACT_MATCH = 0;
    private final Updater updater;
    private final Promotions promotions;
    private final Items items;

    public Convenience() {
        this.updater = new Updater();
        this.promotions = updater.updatePromotions();
        this.items = updater.updateItems();
    }

    public List<ShoppingProduct> checkPurchaseItems(final String input) {
        ProductFormatter productFormatter = new ProductFormatter();
        return productFormatter.convertStringToItem(input, items);
    }

    public boolean isPromotionNotApplicableToAllItems(final ShoppingProduct shoppingProduct) {
        return calculateMaxCountOfPromotionItemsAvailable(shoppingProduct) < shoppingProduct.getQuantity();
    }

    public boolean canReceiveAdditionalBenefit(final ShoppingProduct shoppingProduct) {
        return isLessThanPromotionRemaingStock(shoppingProduct) && isLessThanPromotionStandard(shoppingProduct);
    }

    public int calculateItemCountWithoutPromotion(final ShoppingProduct shoppingProduct) {
        int inputQuantity = shoppingProduct.getQuantity();

        int promoItemsAvailableSize = calculateMaxCountOfPromotionItemsAvailable(shoppingProduct);
        return inputQuantity - promoItemsAvailableSize;
    }

    public List<Item> getItems() {
        return items.getItems();
    }

    private int calculateMaxCountOfPromotionItemsAvailable(final ShoppingProduct shoppingProduct) {
        int remainingStock = items.checkRemainingPromotionStock(shoppingProduct.getName());
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        int fullPromotionGroupCount = remainingStock / promotionGroupSize;

        return fullPromotionGroupCount * promotionGroupSize;
    }

    private boolean isLessThanPromotionRemaingStock(final ShoppingProduct shoppingProduct) {
        return items.checkRemainingPromotionStock(shoppingProduct.getName()) > shoppingProduct.getQuantity();
    }

    private boolean isLessThanPromotionStandard(final ShoppingProduct shoppingProduct) {
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        return shoppingProduct.getQuantity() % promotionGroupSize != EXACT_MATCH;
    }
}
