package store.io.view;


public final class ReceiptFormat {

    private static final String NEW_LINE = System.lineSeparator();
    static final String RECEIPT_HEADER = "==============W 편의점================";
    static final String RECEIPT_GIFT_HEADER = "=============증\t    정===============";
    static final String DIVIDING_LINE = "====================================";

    static final String RECEIPT_INFORMATION_FORMAT = "%-15s %5s %8s" + NEW_LINE; // 기존 포맷 유지
    static final String RECEIPT_GIFT_FORMAT = "%-15s %5s" + NEW_LINE;
    static final String RECEIPT_DISCOUNT_FORMAT = "%-15s -%9s" + NEW_LINE; // - 기호 포함하여 숫자 앞에 위치
    static final String RECEIPT_PAYMENT_FORMAT = "%-15s %10s" + NEW_LINE;

    static final String ITEM_NAME = "상품명";
    static final String ITEM_QUANTITY = "수량";
    static final String ITEM_PRICE = "금액";
    static final String TOTAL_PRICE = "총구매액";
    static final String PROMOTION_DISCOUNT_PRICE = "행사할인";
    static final String MEMBERSHIP_DISCOUNT_PRICE = "멤버십할인";
    static final String PAYMENT = "내실돈";
}
