package store.io.view;

public final class ReceiptFormatter {
    /*

    static final String RECEIPT_HEADER = "==============W 편의점================";
    static final String RECEIPT_GIFT_HEADER = "=============증\t    정===============";
    static final String DIVIDING_LINE = "====================================";

    static final String ITEM_NAME = "상품명";
    static final String ITEM_QUANTITY = "수량";
    static final String ITEM_PRICE = "금액";
    static final String TOTAL_PRICE = "총구매액";
    static final String PROMOTION_DISCOUNT_PRICE = "행사할인";
    static final String MEMBERSHIP_DISCOUNT_PRICE = "멤버십할인";
    static final String PAYMENT = "내실돈";

    private static final int NAME_WIDTH = 15;
    private static final int QUANTITY_WIDTH = 5;
    private static final int PRICE_WIDTH = 10;

    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);


    void printReceipt(final Receipt receipt) {
        printHeader();
        printItemInformation(receipt);
        printGiftInformation(receipt);
        printPriceInformation(receipt);
    }

    private void printHeader() {
        System.out.println(RECEIPT_HEADER);
        System.out.printf("%-15s %5s %8s%n", ITEM_NAME, ITEM_QUANTITY, ITEM_PRICE);
    }

    private void printItemInformation(final Receipt receipt) {
        for (ShoppingProduct product : receipt.purchasedProducts()) {
            String formattedQuantity = numberFormat.format(product.getQuantity());
            String formattedPrice = numberFormat.format(product.getPrice());
            System.out.println(formatLine(product.getName(), formattedQuantity, formattedPrice));
        }
    }

    private void printGiftInformation(final Receipt receipt) {
        System.out.println(RECEIPT_GIFT_HEADER);
        for (Gift gift : receipt.gifts()) {
            System.out.println(formatGiftLine(gift.name(), gift.getQuantity()));
        }
    }

    private void printPriceInformation(final Receipt receipt) {
        System.out.println(DIVIDING_LINE);
        System.out.println(formatLine(TOTAL_PRICE, receipt.getTotalCount(), receipt.getTotalPrice()));
        System.out.println(formatPriceLine(PROMOTION_DISCOUNT_PRICE, receipt.getPromotionDiscountPrice()));
        System.out.println(formatPriceLine(MEMBERSHIP_DISCOUNT_PRICE, receipt.getMemberShipDiscountPrice()));
        System.out.println(formatPriceLine(PAYMENT, receipt.getPayment()));
    }

    private String formatLine(String name, String quantity, String price) {
        return String.format("%-" + NAME_WIDTH + "s %" + QUANTITY_WIDTH + "s %" + PRICE_WIDTH + "s",
                name, quantity, price);
    }

    private String formatGiftLine(String name, String quantity) {
        return String.format("%-" + NAME_WIDTH + "s %" + QUANTITY_WIDTH + "s",
                name, quantity);
    }

    private String formatPriceLine(String label, String price) {
        return String.format("%-" + NAME_WIDTH + "s %" + (QUANTITY_WIDTH + PRICE_WIDTH) + "s",
                label, price);
    }

     */
}
