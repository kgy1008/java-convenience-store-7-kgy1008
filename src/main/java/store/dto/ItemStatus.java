package store.dto;

import java.util.List;
import store.domain.store.item.Item;

public record ItemStatus(
        List<Item> items
) {
    public List<ItemDetail> getItemDetails() {
        return items.stream()
                .map(item -> ItemDetail.of(item.getName(), item.getPrice(), item.getQuantity(),
                        item.getPromotionName()))
                .toList();
    }
}
