package store.dto;

import java.text.NumberFormat;
import java.util.Locale;

public record FreeItem(
        String name,
        int quantity,
        int price,
        int promotionGroupSize
) {
    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);

    public String getFormattedQuantity() {
        return numberFormat.format(this.quantity);
    }
}
