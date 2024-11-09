package store.io.view;

import static store.io.view.ReceiptFormat.DIVIDING_LINE;
import static store.io.view.ReceiptFormat.ITEM_NAME;
import static store.io.view.ReceiptFormat.ITEM_PRICE;
import static store.io.view.ReceiptFormat.ITEM_QUANTITY;
import static store.io.view.ReceiptFormat.MEMBERSHIP_DISCOUNT_PRICE;
import static store.io.view.ReceiptFormat.PAYMENT;
import static store.io.view.ReceiptFormat.PROMOTION_DISCOUNT_PRICE;
import static store.io.view.ReceiptFormat.RECEIPT_DISCOUNT_FORMAT;
import static store.io.view.ReceiptFormat.RECEIPT_GIFT_FORMAT;
import static store.io.view.ReceiptFormat.RECEIPT_GIFT_HEADER;
import static store.io.view.ReceiptFormat.RECEIPT_HEADER;
import static store.io.view.ReceiptFormat.RECEIPT_INFORMATION_FORMAT;
import static store.io.view.ReceiptFormat.RECEIPT_PAYMENT_FORMAT;
import static store.io.view.ReceiptFormat.TOTAL_PRICE;

import java.text.NumberFormat;
import java.util.Locale;
import store.domain.user.ShoppingProduct;
import store.dto.Gift;
import store.dto.ItemStatus;
import store.dto.Receipt;

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
        System.out.print(NEW_LINE);
    }

    public void printReceipt(final Receipt receipt) {
        System.out.println(NEW_LINE + RECEIPT_HEADER);
        printItemInformation(receipt);
        printGiftInformation(receipt);
        System.out.println(DIVIDING_LINE);
        printPriceInformation(receipt);
    }

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }

    private String formatQuantity(final int quantity) {
        if (quantity == 0) {
            return SOLD_OUT;
        }
        return quantity + QUANTITY_UNIT;
    }

    private void printItemInformation(final Receipt receipt) {
        System.out.printf(RECEIPT_INFORMATION_FORMAT, ITEM_NAME, ITEM_QUANTITY, ITEM_PRICE);
        for (ShoppingProduct product : receipt.purchasedProducts()) {
            System.out.printf(RECEIPT_INFORMATION_FORMAT, product.getName(), numberFormat.format(product.getQuantity()),
                    numberFormat.format(product.getPrice()));
        }
    }

    private void printGiftInformation(final Receipt receipt) {
        System.out.println(RECEIPT_GIFT_HEADER);
        for (Gift gift : receipt.gifts()) {
            System.out.printf(RECEIPT_GIFT_FORMAT, gift.name(), numberFormat.format(gift.quantity()));
        }
    }

    private void printPriceInformation(final Receipt receipt) {
        System.out.printf(RECEIPT_INFORMATION_FORMAT, TOTAL_PRICE, numberFormat.format(receipt.totalCount()),
                numberFormat.format(receipt.totalPrice()));
        System.out.printf(RECEIPT_DISCOUNT_FORMAT, PROMOTION_DISCOUNT_PRICE,
                numberFormat.format(receipt.promotionDiscountPrice()));
        System.out.printf(RECEIPT_DISCOUNT_FORMAT, MEMBERSHIP_DISCOUNT_PRICE,
                numberFormat.format(receipt.memberShipDiscountPrice()));
        System.out.printf(RECEIPT_PAYMENT_FORMAT, PAYMENT, numberFormat.format(receipt.payment()));
    }
}
