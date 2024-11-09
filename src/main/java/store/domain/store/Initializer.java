package store.domain.store;

import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;
import store.io.provider.ProductFileReader;
import store.io.provider.PromotionFileReader;

public class Initializer {
    private final ProductFileReader productFileReader;
    private final PromotionFileReader promotionFileReader;

    Initializer() {
        this.productFileReader = new ProductFileReader();
        this.promotionFileReader = new PromotionFileReader();
    }

    Promotions initPromotions() {
        return promotionFileReader.getPromotions();
    }

    Items initItems() {
        return productFileReader.getItems();
    }
}
