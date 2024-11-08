package store.domain.store;

import java.util.List;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;

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
}
