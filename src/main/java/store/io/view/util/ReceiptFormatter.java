package store.io.view.util;

import java.text.NumberFormat;
import java.util.Locale;
import store.domain.store.item.BasicItem;
import store.domain.store.item.PromotionItem;
import store.dto.FreeItem;
import store.dto.Receipt;

public final class ReceiptFormatter {

    public static final String RECEIPT_HEADER = "==============W 편의점================";
    public static final String RECEIPT_GIFT_HEADER = "=============증\t    정===============";
    public static final String DIVIDING_LINE = "====================================";

    public static final String ITEM_NAME = "상품명";
    public static final String ITEM_QUANTITY = "수량";
    public static final String ITEM_PRICE = "금액";
    public static final String TOTAL_PRICE = "총구매액";
    public static final String PROMOTION_DISCOUNT_PRICE = "행사할인";
    public static final String MEMBERSHIP_DISCOUNT_PRICE = "멤버십할인";
    public static final String PAYMENT = "내실돈";

    private static final String NEW_LINE = System.lineSeparator();
    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);

    public void printReceipt(final Receipt receipt) {
        printHeader();
        printItemInformation(receipt);
        printGiftInformation(receipt);
        printPriceInformation(receipt);
    }

    private void printHeader() {
        System.out.println(RECEIPT_HEADER);
        System.out.printf("%-18s %-7s %s%s", ITEM_NAME, ITEM_QUANTITY, ITEM_PRICE, NEW_LINE);
    }

    private void printItemInformation(final Receipt receipt) {
        printPromotionItemInformation(receipt);
        printBasicItemInformation(receipt);
    }

    private void printPromotionItemInformation(final Receipt receipt) {
        for (PromotionItem promotionItem : receipt.promotionItems()) {
            String formattedQuantity = numberFormat.format(promotionItem.getQuantity());
            String formattedTotalPriceOfItem = numberFormat.format(
                    promotionItem.getPrice() * promotionItem.getQuantity());
            System.out.printf("%-18s %-8s %s%s", promotionItem.getName(), formattedQuantity, formattedTotalPriceOfItem,
                    NEW_LINE);
        }
    }


    private void printBasicItemInformation(final Receipt receipt) {
        for (BasicItem basicItem : receipt.basicItems()) {
            String formattedQuantity = numberFormat.format(basicItem.getQuantity());
            String formattedPrice = numberFormat.format(basicItem.getPrice() * basicItem.getQuantity());
            System.out.printf("%-18s %-8s %s%s", basicItem.getName(), formattedQuantity, formattedPrice, NEW_LINE);
        }
    }

    private void printGiftInformation(final Receipt receipt) {
        System.out.println(RECEIPT_GIFT_HEADER);
        for (FreeItem freeItem : receipt.freeItems()) {
            System.out.printf("%-18s %-8s %s", freeItem.name(), freeItem.getFormattedQuantity(), NEW_LINE);
        }
    }

    private void printPriceInformation(final Receipt receipt) {
        System.out.println(DIVIDING_LINE);
        System.out.printf("%-18s %-7s %s%s", TOTAL_PRICE, receipt.getFormattedTotalCount(),
                receipt.getFormattedTotalPrice(), NEW_LINE);
        System.out.printf("%-27s %s %s", PROMOTION_DISCOUNT_PRICE, receipt.getFormattedPromotionDiscountPrice(),
                NEW_LINE);
        System.out.printf("%-27s %s %s", MEMBERSHIP_DISCOUNT_PRICE, receipt.getFormattedMembershipDiscountPrice(),
                NEW_LINE);
        System.out.printf("%-28s %s %s", PAYMENT, receipt.getFormattedPayment(), NEW_LINE);
    }
}
