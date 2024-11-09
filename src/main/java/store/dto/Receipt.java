package store.dto;

import java.util.List;
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
    public static Receipt generate(
            final List<ShoppingProduct> purchasedProducts,
            final List<Gift> gifts,
            final int totalPrice,
            final int promotionDiscountPrice,
            final int memberShipDiscountPrice,
            final int payment,
            final int totalCount) {
        return new Receipt(purchasedProducts, gifts, totalPrice, promotionDiscountPrice, memberShipDiscountPrice,
                payment, totalCount);
    }
}