package store.domain.store;

import java.util.ArrayList;
import java.util.List;
import store.domain.store.item.BasicItem;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;
import store.domain.user.ShoppingProduct;
import store.domain.user.ShoppingProducts;
import store.dto.FreeItem;
import store.dto.Receipt;

public class Cashier {

    private static final int FREE_BENEFIT = 1;

    private final Convenience convenience;
    private final List<PromotionItem> promotionItems;
    private final List<BasicItem> basicItems;

    public Cashier(final Convenience convenience) {
        this.convenience = convenience;
        this.promotionItems = new ArrayList<>();
        this.basicItems = new ArrayList<>();
    }

    public void receiveAndClassifyItems(final ShoppingProducts shoppingProducts) {
        final Items items = convenience.getItems();
        for (ShoppingProduct shoppingProduct : shoppingProducts.getProducts()) {
            String name = shoppingProduct.getName();
            Item item = items.findItemByName(name);
            if (convenience.isPromotionApplicableToday(shoppingProduct)) {
                extractPromotionItems(shoppingProduct, item);
                continue;
            }
            extractBasicItems(shoppingProduct, item);
        }
    }

    private void extractPromotionItems(final ShoppingProduct shoppingProduct, final Item item) {
        PromotionItem promotionItem = new PromotionItem(item.getName(), item.getPrice(), shoppingProduct.getQuantity(),
                item.getPromotionName());
        promotionItems.add(promotionItem);
    }

    private void extractBasicItems(final ShoppingProduct shoppingProduct, final Item item) {
        BasicItem basicItem = new BasicItem(item.getName(), item.getPrice(), shoppingProduct.getQuantity());
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
    }

    public void addPromotionItemFromCart(final PromotionItem promotionItem) {
        promotionItem.increaseQuantity(FREE_BENEFIT);
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
                memberShipDiscountPrice,
                payment, totalCount);
    }

    private List<FreeItem> findFreeItem(final List<PromotionItem> promotionItems) {
        return promotionItems.stream()
                .map(product -> new FreeItem(product.getName(),
                        convenience.calculateNumberOfFreeItem(product),
                        product.getPrice(),
                        convenience.findPromotionBundleSize(product)))
                .toList();
    }

    public void finishPayment() {
        convenience.updateItemQuantity(promotionItems, basicItems);
        promotionItems.clear();
        basicItems.clear();
    }
}
