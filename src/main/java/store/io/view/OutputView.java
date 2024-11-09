package store.io.view;

import store.dto.ItemStatus;

public class OutputView {

    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String PRODUCT_STATUS_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String SOLD_OUT = "재고 없음";
    private static final String QUANTITY_UNIT = "개";
    private static final String NEW_LINE = System.lineSeparator();

    public void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void printProducts(ItemStatus itemStatus) {
        System.out.println(PRODUCT_STATUS_MESSAGE + NEW_LINE);
        itemStatus.getItemDetails().forEach(itemDetail ->
                System.out.printf("- %s %d원 %s %s%s", itemDetail.name(), itemDetail.price(),
                        formatQuantity(itemDetail.quantity()), itemDetail.promotion(), NEW_LINE)
        );
        System.out.print(NEW_LINE);
    }

    private String formatQuantity(final int quantity) {
        if (quantity == 0) {
            return SOLD_OUT;
        }
        return quantity + QUANTITY_UNIT;
    }

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }
}
