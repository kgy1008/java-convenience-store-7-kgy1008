package store.domain.store;

import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;
import store.io.provider.ProductFileReader;
import store.io.provider.PromotionFileReader;

public class StoreUpdater {
    private final ProductFileReader productFileReader;
    private final PromotionFileReader promotionFileReader;

    StoreUpdater() {
        this.productFileReader = new ProductFileReader();
        this.promotionFileReader = new PromotionFileReader();
    }

    Promotions updatePromotions() {
        return promotionFileReader.getPromotions();
    }

    Items updateItems() {
        return productFileReader.getItems();
    }
}
