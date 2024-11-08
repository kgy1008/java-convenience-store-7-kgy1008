package store.domain.store;

import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;
import store.io.provider.ProductFileReader;
import store.io.provider.PromotionFileReader;

public class StoreInitializer {
    private final ProductFileReader productFileReader;
    private final PromotionFileReader promotionFileReader;

    StoreInitializer() {
        this.productFileReader = new ProductFileReader();
        this.promotionFileReader = new PromotionFileReader();
    }

    Promotions initPromotions() {
        return promotionFileReader.getPromotions();
    }

    Items initItems(final Promotions promotions) {
        return productFileReader.getItems(promotions);
    }
}
