package store.domain.store;

import java.util.List;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;
import store.domain.store.util.ProductFormatter;
import store.domain.user.ShoppingProduct;

public class Convenience {
    private final StoreInitializer initializer;
    private final Promotions promotions;
    private final Items items;

    public Convenience() {
        this.initializer = new StoreInitializer();
        this.promotions = initializer.initPromotions();
        this.items = initializer.initItems(promotions);
    }

    public List<Item> getItems() {
        return items.getItems();
    }

    public List<ShoppingProduct> checkPurchaseItems(final String input) {
        ProductFormatter productFormatter = new ProductFormatter();
        return productFormatter.convertStringToItem(input, items);
    }

    public boolean isExceedPromotionRemaingStock(final ShoppingProduct shoppingProduct) {
        return items.checkRemainingPromotionStock(shoppingProduct.getName()) < shoppingProduct.getQuantity();
    }

    public int getItemCountWithoutPromotion(final ShoppingProduct shoppingProduct) {
        int remainingStock = items.checkRemainingPromotionStock(shoppingProduct.getName());
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName());
        int fullPromotionGroupCount = remainingStock / promotionGroupSize;
        int inputQuantity = shoppingProduct.getQuantity();
        return inputQuantity - (fullPromotionGroupCount * promotionGroupSize);
    }
}
