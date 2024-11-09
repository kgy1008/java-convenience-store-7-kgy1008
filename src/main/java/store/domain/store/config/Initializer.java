package store.domain.store.config;

import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;
import store.io.provider.ProductFileReader;
import store.io.provider.PromotionFileReader;

public class Initializer {
    private final ProductFileReader productFileReader;
    private final PromotionFileReader promotionFileReader;

    public Initializer() {
        this.productFileReader = new ProductFileReader();
        this.promotionFileReader = new PromotionFileReader();
    }

    public Promotions initPromotions() {
        return promotionFileReader.getPromotions();
    }

    public Items initItems() {
        return productFileReader.getItems();
    }
}
