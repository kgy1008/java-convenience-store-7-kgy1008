package store.domain.store;

import java.util.List;
import store.domain.store.item.BasicItem;
import store.domain.store.item.PromotionItem;
import store.dto.FreeItem;

public class Calculator {

    private static final int NO_MEMBERSHIP_DISCOUNT = 0;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;

    private final List<FreeItem> freeItems;

    public Calculator(final List<FreeItem> freeItems) {
        this.freeItems = freeItems;
    }

    int calculateTotalPrice(final List<BasicItem> basicItems, final List<PromotionItem> promotionItems) {
        int basicItemsPrice = calculatePriceOfBasicItems(basicItems);
        int promotionItemsPrice = calculatePriceOfPromotionItems(promotionItems);
        return basicItemsPrice + promotionItemsPrice;
    }

    private int calculatePriceOfBasicItems(final List<BasicItem> basicItems) {
        return basicItems.stream()
                .mapToInt(basicItem -> basicItem.getPrice() * basicItem.getQuantity())
                .sum();
    }

    private int calculatePriceOfPromotionItems(final List<PromotionItem> promotionItems) {
        return promotionItems.stream()
                .mapToInt(promotionItem -> promotionItem.getPrice() * promotionItem.getQuantity())
                .sum();
    }

    int calculatePromotionDiscountPrice() {
        return freeItems.stream()
                .mapToInt(freeItem -> freeItem.price() * freeItem.quantity())
                .sum();
    }

    int calculateMemberShipDiscountPrice(final boolean hasMembershipBenefit, final int totalPrice) {
        if (hasMembershipBenefit) {
            int targetPrice = calculateTargetPrice(totalPrice);
            int finalPrice = (int) (targetPrice * MEMBERSHIP_DISCOUNT_RATE);
            return Math.min(MAX_MEMBERSHIP_DISCOUNT, finalPrice);
        }
        return NO_MEMBERSHIP_DISCOUNT;
    }

    private int calculateTargetPrice(final int totalPrice) {
        int noMemberShipDiscountTargetPrice = freeItems.stream()
                .mapToInt(freeItem -> freeItem.quantity() * freeItem.price() * freeItem.promotionGroupSize())
                .sum();
        return totalPrice - noMemberShipDiscountTargetPrice;
    }

    int calculateTotalCount(final List<BasicItem> basicItems, final List<PromotionItem> promotionItems) {
        int basicItemsCount = calculateNumberOfBasicItems(basicItems);
        int promotionItemsCount = calculateNumberOfPromotionItems(promotionItems);
        return basicItemsCount + promotionItemsCount;
    }

    private int calculateNumberOfBasicItems(final List<BasicItem> basicItems) {
        return basicItems.stream()
                .mapToInt(BasicItem::getQuantity)
                .sum();
    }

    private int calculateNumberOfPromotionItems(final List<PromotionItem> promotionItems) {
        return promotionItems.stream()
                .mapToInt(PromotionItem::getQuantity)
                .sum();
    }
}
