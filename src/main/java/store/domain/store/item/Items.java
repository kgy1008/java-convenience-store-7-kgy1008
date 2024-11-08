package store.domain.store.item;

import java.util.Collections;
import java.util.List;

public class Items {
    private final List<Item> items;

    public Items(final List<Item> items) {
        this.items = items;
    }

    public boolean isNotContain(final String name) {
        return items.stream()
                .noneMatch(item -> item.isEqual(name));
    }

    public int checkRemainingStock(final String name) {
        return items.stream()
                .filter(item -> item.isEqual(name))
                .mapToInt(Item::getQuantity)
                .sum();
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
