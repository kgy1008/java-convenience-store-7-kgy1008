package store.dto;

import java.util.List;
import store.domain.store.item.BasicItem;
import store.domain.store.item.PromotionItem;

public record Receipt(
        List<PromotionItem> promotionItems,
        List<BasicItem> basicItems,
        List<FreeItem> freeItems,
        int totalPrice,
        int promotionDiscountPrice,
        int membershipDiscountPrice,
        int payment,
        int totalCount
) {
    private static final String MINUS = "-";

    public static Receipt from(final List<PromotionItem> promotionItems, final List<BasicItem> basicItems,
                               final List<FreeItem> freeItems,
                               final int totalPrice, final int promotionDiscountPrice,
                               final int membershipDiscountPrice, final int payment, final int totalCount) {
        return new Receipt(promotionItems, basicItems, freeItems, totalPrice, promotionDiscountPrice,
                membershipDiscountPrice,
                payment, totalCount);
    }
}
