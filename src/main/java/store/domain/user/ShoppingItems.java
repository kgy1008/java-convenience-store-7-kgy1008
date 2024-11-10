package store.domain.user;

import java.util.Collections;
import java.util.List;

public class ShoppingItems {

    private final List<ShoppingItem> Items;

    public ShoppingItems(final List<ShoppingItem> Items) {
        this.Items = Items;
    }

    public List<ShoppingItem> getItems() {
        return Collections.unmodifiableList(Items);
    }
}
