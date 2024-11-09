package store.dto;

public record Gift(
        String name,
        int quantity,
        int price,
        int promotionGroupSize
) {
}
