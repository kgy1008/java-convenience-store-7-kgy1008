package store.dto;

public record ItemDetail(
        String name,
        int price,
        int quantity,
        String promotion
) {
    public static ItemDetail of(String name, int price, int quantity, String promotion) {
        return new ItemDetail(name, price, quantity, promotion);
    }
}
