package store.dto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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
    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);
    private static final String MINUS = "-";

    public static Receipt from(final List<PromotionItem> promotionItems, final List<BasicItem> basicItems,
                               final List<FreeItem> freeItems,
                               final int totalPrice, final int promotionDiscountPrice,
                               final int membershipDiscountPrice, final int payment, final int totalCount) {
        return new Receipt(promotionItems, basicItems, freeItems, totalPrice, promotionDiscountPrice,
                membershipDiscountPrice,
                payment, totalCount);
    }

    public String getFormattedTotalPrice() {
        return numberFormat.format(totalPrice);
    }

    public String getFormattedPromotionDiscountPrice() {
        return MINUS + numberFormat.format(promotionDiscountPrice);
    }

    public String getFormattedMembershipDiscountPrice() {
        return MINUS + numberFormat.format(membershipDiscountPrice);
    }

    public String getFormattedPayment() {
        return numberFormat.format(payment);
    }

    public String getFormattedTotalCount() {
        return numberFormat.format(totalCount);
    }
}
