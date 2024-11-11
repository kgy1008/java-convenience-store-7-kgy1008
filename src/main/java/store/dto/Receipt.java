package store.dto;

import java.util.List;
import store.domain.store.item.BasicItem;
import store.domain.store.item.PromotionItem;

public record Receipt(
        List<PromotionItem> promotionItems,
        List<BasicItem> basicItems,
        List<FreeItem> freeItems,
        PriceInformation priceInformation
) {
}
