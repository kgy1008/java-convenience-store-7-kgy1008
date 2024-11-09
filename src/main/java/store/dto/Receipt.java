package store.dto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import store.domain.user.ShoppingProduct;

public record Receipt(
        List<ShoppingProduct> purchasedProducts,
        List<Gift> gifts,
        int totalPrice,
        int promotionDiscountPrice,
        int memberShipDiscountPrice,
        int payment,
        int totalCount
) {
    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);
    private static final String MINUS = "-";

    public String getTotalPrice() {
        return numberFormat.format(totalPrice);
    }

    public String getTotalCount() {
        return numberFormat.format(totalCount);
    }

    public String getPromotionDiscountPrice() {
        return MINUS + numberFormat.format(promotionDiscountPrice);
    }

    public String getMemberShipDiscountPrice() {
        return MINUS + numberFormat.format(memberShipDiscountPrice);
    }

    public String getPayment() {
        return numberFormat.format(payment);
    }
}
