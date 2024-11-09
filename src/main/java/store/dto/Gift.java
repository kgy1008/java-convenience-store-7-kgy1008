package store.dto;

import java.text.NumberFormat;
import java.util.Locale;

public record Gift(
        String name,
        int quantity,
        int price,
        int promotionGroupSize
) {
    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);

    public String getQuantity() {
        return numberFormat.format(quantity);
    }

    public String getPrice() {
        return numberFormat.format(price);
    }
}
