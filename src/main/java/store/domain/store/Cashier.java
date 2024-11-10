package store.domain.store;

import java.util.ArrayList;
import java.util.List;
import store.domain.store.item.BasicItem;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;
import store.domain.user.ShoppingItem;
import store.domain.user.ShoppingItems;
import store.dto.FreeItem;
import store.dto.Receipt;

public class Cashier {

    private static final int FREE_BENEFIT = 1;
    private static final int EMPTY = 0;

    private final Convenience convenience;
    private final List<PromotionItem> promotionItems;
    private final List<BasicItem> basicItems;

    public Cashier(final Convenience convenience) {
        this.convenience = convenience;
        this.promotionItems = new ArrayList<>();
        this.basicItems = new ArrayList<>();
    }

    public void receiveAndClassifyItems(final ShoppingItems shoppingItems) {
        final Items items = convenience.getItems();
        for (ShoppingItem shoppingItem : shoppingItems.getItems()) {
            String name = shoppingItem.getName();
            Item item = items.findItemByName(name);
            if (convenience.isPromotionApplicableToday(shoppingItem)) {
                extractPromotionItems(shoppingItem, item);
                continue;
            }
            extractBasicItems(shoppingItem, item);
        }
    }

    private void extractPromotionItems(final ShoppingItem shoppingItem, final Item item) {
        PromotionItem promotionItem = new PromotionItem(item.getName(), item.getPrice(), shoppingItem.getQuantity(),
                item.getPromotionName());
        promotionItems.add(promotionItem);
    }

    private void extractBasicItems(final ShoppingItem shoppingItem, final Item item) {
        BasicItem basicItem = new BasicItem(item.getName(), item.getPrice(), shoppingItem.getQuantity());
        basicItems.add(basicItem);
    }

    public List<PromotionItem> getExceedingPromotionItems() {
        return promotionItems.stream()
                .filter(convenience::isPromotionNotApplicableToAllItems)
                .toList();
    }

    public List<PromotionItem> getShortagePromotionItems() {
        return promotionItems.stream()
                .filter(convenience::canReceiveAdditionalBenefit)
                .toList();
    }

    public void removePromotionItemFromCart(final PromotionItem promotionItem, final int itemsWithoutPromotionCount) {
        promotionItem.decreaseQuantity(itemsWithoutPromotionCount);
        if (promotionItem.getQuantity() == EMPTY) {
            promotionItems.remove(promotionItem);
        }
    }

    public void addPromotionItemFromCart(final PromotionItem promotionItem) {
        promotionItem.increaseQuantity(FREE_BENEFIT);
    }

    public boolean isCartNotEmpty() {
        int totalCartSize = basicItems.size() + promotionItems.size();
        return totalCartSize > 0;
    }

    public Receipt generateReceipt(final boolean hasMembershipBenefit) {
        List<FreeItem> freeItems = findFreeItem(promotionItems);
        Calculator calculator = new Calculator(freeItems);

        int totalPrice = calculator.calculateTotalPrice(basicItems, promotionItems);
        int promotionDiscountPrice = calculator.calculatePromotionDiscountPrice();
        int memberShipDiscountPrice = calculator.calculateMemberShipDiscountPrice(hasMembershipBenefit, totalPrice);
        int payment = totalPrice - (promotionDiscountPrice + memberShipDiscountPrice);
        int totalCount = calculator.calculateTotalCount(basicItems, promotionItems);

        return Receipt.from(promotionItems, basicItems, freeItems, totalPrice, promotionDiscountPrice,
                memberShipDiscountPrice, payment, totalCount);
    }

    private List<FreeItem> findFreeItem(final List<PromotionItem> promotionItems) {
        return promotionItems.stream()
                .map(item -> new FreeItem(item.getName(),
                        convenience.calculateNumberOfFreeItem(item),
                        item.getPrice(),
                        convenience.findPromotionBundleSize(item)))
                .toList();
    }

    public void finishPayment() {
        convenience.updateItemQuantity(promotionItems, basicItems);
        promotionItems.clear();
        basicItems.clear();
    }
}
