package store.io.view;

import java.text.NumberFormat;
import java.util.Locale;
import store.dto.ItemStatus;

public class OutputView {

    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String PRODUCT_STATUS_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String SOLD_OUT = "재고 없음";
    private static final String QUANTITY_UNIT = "개";
    private static final String NEW_LINE = System.lineSeparator();
    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);

    public void printWelcomeMessage() {
        System.out.println(NEW_LINE + WELCOME_MESSAGE);
    }

    public void printProducts(ItemStatus itemStatus) {
        System.out.println(PRODUCT_STATUS_MESSAGE + NEW_LINE);
        itemStatus.getItemDetails().forEach(itemDetail -> System.out.printf("- %s %s원 %s %s%s", itemDetail.name(),
                numberFormat.format(itemDetail.price()), formatQuantity(itemDetail.quantity()), itemDetail.promotion(),
                NEW_LINE));
    }

    /*
    public void printReceipt(final Receipt receipt) {
        ReceiptFormatter receiptFormatter = new ReceiptFormatter();
        receiptFormatter.printReceipt(receipt);
    }

     */

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }

    private String formatQuantity(final int quantity) {
        if (quantity == 0) {
            return SOLD_OUT;
        }
        return quantity + QUANTITY_UNIT;
    }
}
