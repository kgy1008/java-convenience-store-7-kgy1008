package store.io.view.util;

import store.domain.store.item.BasicItem;
import store.domain.store.item.PromotionItem;
import store.dto.FreeItem;
import store.dto.PriceInformation;
import store.dto.Receipt;

public final class ReceiptFormatter {

    private static final String RECEIPT_HEADER = "==============W 편의점================";
    private static final String RECEIPT_GIFT_HEADER = "=============증\t    정===============";
    private static final String DIVIDING_LINE = "====================================";

    private static final String ITEM_NAME = "상품명";
    private static final String ITEM_QUANTITY = "수량";
    private static final String ITEM_PRICE = "금액";
    private static final String TOTAL_PRICE = "총구매액";
    private static final String PROMOTION_DISCOUNT_PRICE = "행사할인";
    private static final String MEMBERSHIP_DISCOUNT_PRICE = "멤버십할인";
    private static final String PAYMENT = "내실돈";

    private static final String FORMAT = "%-28s %s %s";
    private static final String INFO_FORMAT = "%-18s %,-8d %,d%s";

    private static final String MINUS = "-";
    private static final String NEW_LINE = System.lineSeparator();

    public void printReceipt(final Receipt receipt) {
        printHeader();
        printItemInformation(receipt);
        printGiftInformation(receipt);
        printPriceInformation(receipt.priceInformation());
    }

    private void printHeader() {
        System.out.println(RECEIPT_HEADER);
        System.out.printf("%-18s %-8s %s%s", ITEM_NAME, ITEM_QUANTITY, ITEM_PRICE, NEW_LINE);
    }

    private void printItemInformation(final Receipt receipt) {
        printPromotionItemInformation(receipt);
        printBasicItemInformation(receipt);
    }

    private void printPromotionItemInformation(final Receipt receipt) {
        for (PromotionItem promotionItem : receipt.promotionItems()) {
            int totalPriceOfItem = promotionItem.getPrice() * promotionItem.getQuantity();
            System.out.printf(INFO_FORMAT, promotionItem.getName(), promotionItem.getQuantity(), totalPriceOfItem,
                    NEW_LINE);
        }
    }

    private void printBasicItemInformation(final Receipt receipt) {
        for (BasicItem basicItem : receipt.basicItems()) {
            int price = basicItem.getPrice() * basicItem.getQuantity();
            System.out.printf(INFO_FORMAT, basicItem.getName(), basicItem.getQuantity(), price, NEW_LINE);
        }
    }

    private void printGiftInformation(final Receipt receipt) {
        System.out.println(RECEIPT_GIFT_HEADER);
        for (FreeItem freeItem : receipt.freeItems()) {
            System.out.printf("%-18s %-8s %s", freeItem.name(), freeItem.quantity(), NEW_LINE);
        }
    }

    private void printPriceInformation(final PriceInformation priceInformation) {
        System.out.println(DIVIDING_LINE);
        System.out.printf(INFO_FORMAT, TOTAL_PRICE, priceInformation.totalCount(), priceInformation.totalPrice(),
                NEW_LINE);

        String formattedPromotionDiscountPrice = getFormattedString(priceInformation.promotionDiscountPrice());
        System.out.printf(FORMAT, PROMOTION_DISCOUNT_PRICE, formattedPromotionDiscountPrice, NEW_LINE);

        String formattedMembershipDiscountPrice = getFormattedString(priceInformation.membershipDiscountPrice());
        System.out.printf(FORMAT, MEMBERSHIP_DISCOUNT_PRICE, formattedMembershipDiscountPrice, NEW_LINE);
        System.out.printf(FORMAT, PAYMENT, String.format("%,d", priceInformation.payment()), NEW_LINE);
    }

    private String getFormattedString(final int input) {
        return String.format("%s%,d", MINUS, input);
    }
}
