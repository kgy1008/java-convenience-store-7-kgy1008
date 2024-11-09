package store.domain.store;

import java.util.List;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;
import store.domain.store.util.ProductFormatter;
import store.domain.user.ShoppingProduct;

public class Convenience {

    private static final int EXACT_MATCH = 0;
    private final StoreUpdater initializer;
    private final Promotions promotions;
    private final Items items;

    public Convenience() {
        this.initializer = new StoreUpdater();
        this.promotions = initializer.updatePromotions();
        this.items = initializer.updateItems();
    }

    public List<Item> getItems() {
        return items.getItems();
    }

    public List<ShoppingProduct> checkPurchaseItems(final String input) {
        ProductFormatter productFormatter = new ProductFormatter();
        return productFormatter.convertStringToItem(input, items);
    }

    public boolean isGreaterThanPromotionRemaingStock(final ShoppingProduct shoppingProduct) {
        return items.checkRemainingPromotionStock(shoppingProduct.getName()) < shoppingProduct.getQuantity();
    }

    public boolean canReceiveAdditionalBenefit(final ShoppingProduct shoppingProduct) {
        return isLessThanPromotionRemaingStock(shoppingProduct) && isLessThanPromotionStandard(shoppingProduct);
    }

    private boolean isLessThanPromotionRemaingStock(final ShoppingProduct shoppingProduct) {
        return items.checkRemainingPromotionStock(shoppingProduct.getName()) > shoppingProduct.getQuantity();
    }

    private boolean isLessThanPromotionStandard(final ShoppingProduct shoppingProduct) {
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        return shoppingProduct.getQuantity() % promotionGroupSize != EXACT_MATCH;
    }

    public int getItemCountWithoutPromotion(final ShoppingProduct shoppingProduct) {
        int remainingStock = items.checkRemainingPromotionStock(shoppingProduct.getName());
        int inputQuantity = shoppingProduct.getQuantity();

        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        int fullPromotionGroupCount = remainingStock / promotionGroupSize;

        return inputQuantity - (fullPromotionGroupCount * promotionGroupSize);
    }
}
