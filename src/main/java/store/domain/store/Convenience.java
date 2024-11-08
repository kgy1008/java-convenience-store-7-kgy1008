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
}
