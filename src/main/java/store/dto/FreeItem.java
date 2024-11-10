package store.dto;

public record FreeItem(
        String name,
        int quantity,
        int price,
        int promotionGroupSize
) {
}
