package store.domain.store;

import java.util.ArrayList;
import java.util.List;
import store.domain.store.item.BasicItem;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;
import store.domain.user.ShoppingProduct;
import store.domain.user.ShoppingProducts;

public class Cashier {

    private final List<PromotionItem> promotions;
    private final List<BasicItem> basicItems;
    private final Calculator calculator;

    public Cashier() {
        this.promotions = new ArrayList<>();
        this.basicItems = new ArrayList<>();
        this.calculator = new Calculator();
    }

    public void receiveAndClassifyItems(final ShoppingProducts shoppingProducts, final Convenience convenience) {
        Items items = convenience.getItems();
        for (ShoppingProduct shoppingProduct : shoppingProducts.getProducts()) {
            String name = shoppingProduct.getName();
            Item item = items.findItemByName(name);
            if (convenience.isPromotionApplicableToday(shoppingProduct)) {
                extractPromotionItems(shoppingProduct, item);
                continue;
            }
            extractBasicItems(shoppingProduct, item);
        }
    }

    private void extractPromotionItems(final ShoppingProduct shoppingProduct, final Item item) {
        PromotionItem promotionItem = new PromotionItem(item.getName(), item.getPrice(), shoppingProduct.getQuantity(),
                item.getPromotionName());
        promotions.add(promotionItem);
    }

    private void extractBasicItems(final ShoppingProduct shoppingProduct, final Item item) {
        BasicItem basicItem = new BasicItem(item.getName(), item.getPrice(), shoppingProduct.getQuantity());
        basicItems.add(basicItem);
    }
}
