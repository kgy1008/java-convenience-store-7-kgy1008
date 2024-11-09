package store.domain.store.util;

import java.util.List;
import store.domain.store.CalculableItem;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.user.ShoppingProduct;

public class CartItemFormatter {

    public List<CalculableItem> convertToCalculableItem(final List<ShoppingProduct> carts, final Items items) {
        return carts.stream()
                .map(shoppingProduct -> {
                    String itemName = shoppingProduct.getName();
                    int price = findPriceOfItem(items, itemName);
                    return new CalculableItem(itemName, shoppingProduct.getQuantity(), price);
                })
                .toList();
    }

    private int findPriceOfItem(final Items items, final String itemName) {
        Item item = items.findItemByName(itemName);
        return item.getPrice();
    }
}
