package store.io.view;

import java.text.NumberFormat;
import java.util.Locale;
import store.domain.store.item.BasicItem;
import store.domain.store.item.PromotionItem;
import store.dto.FreeItem;
import store.dto.Receipt;

public final class ReceiptFormatter {


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
        printPromotionItemInformation(receipt);
        printBasicItemInformation(receipt);
    }

    private void printPromotionItemInformation(final Receipt receipt) {
        for (PromotionItem promotionItem : receipt.promotionItems()) {
            String formattedQuantity = numberFormat.format(promotionItem.getQuantity());
            String formattedPrice = numberFormat.format(promotionItem.getPrice());
            System.out.println(formatLine(promotionItem.getName(), formattedQuantity, formattedPrice));
        }
    }

    private void printBasicItemInformation(final Receipt receipt) {
        for (BasicItem basicItem : receipt.basicItems()) {
            String formattedQuantity = numberFormat.format(basicItem.getQuantity());
            String formattedPrice = numberFormat.format(basicItem.getPrice());
            System.out.println(formatLine(basicItem.getName(), formattedQuantity, formattedPrice));
        }
    }

    private void printGiftInformation(final Receipt receipt) {
        System.out.println(RECEIPT_GIFT_HEADER);
        for (FreeItem freeItem : receipt.freeItems()) {
            System.out.println(formatGiftLine(freeItem.name(), freeItem.getFormattedQuantity()));
        }
    }

    private void printPriceInformation(final Receipt receipt) {
        System.out.println(DIVIDING_LINE);
        System.out.println(formatLine(TOTAL_PRICE, receipt.getFormattedTotalCount(), receipt.getFormattedTotalPrice()));
        System.out.println(formatPriceLine(PROMOTION_DISCOUNT_PRICE, receipt.getFormattedPromotionDiscountPrice()));
        System.out.println(formatPriceLine(MEMBERSHIP_DISCOUNT_PRICE, receipt.getFormattedMembershipDiscountPrice()));
        System.out.println(formatPriceLine(PAYMENT, receipt.getFormattedPayment()));
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
}
