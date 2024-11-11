package store.dto;

public record PriceInformation(
        int totalPrice,
        int promotionDiscountPrice,
        int membershipDiscountPrice,
        int payment,
        int totalCount
) {
}
