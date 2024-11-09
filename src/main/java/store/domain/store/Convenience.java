package store.domain.store;

import java.util.List;
import store.domain.store.item.Item;
import store.domain.store.item.Items;
import store.domain.store.promotion.Promotions;

public class Convenience {

    private static final int EXACT_MATCH = 0;
    private final Initializer initializer;
    private final Promotions promotions;
    private final Items items;

    public Convenience() {
        this.initializer = new Initializer();
        this.promotions = initializer.initPromotions();
        this.items = initializer.initItems();
    }

    /*

    public List<ShoppingProduct> checkPurchaseItems(final String input) {
        ProductFormatter productFormatter = new ProductFormatter();
        return productFormatter.convertStringToItem(input, items);
    }

    public boolean isPromotionNotApplicableToAllItems(final ShoppingProduct shoppingProduct) {
        return calculateMaxCountOfPromotionItemsAvailable(shoppingProduct) < shoppingProduct.getQuantity();
    }

    public boolean canReceiveAdditionalBenefit(final ShoppingProduct shoppingProduct) {
        return isLessThanPromotionRemainingStock(shoppingProduct) && isLessThanPromotionStandard(shoppingProduct);
    }

    public int calculateItemCountWithoutPromotion(final ShoppingProduct shoppingProduct) {
        int inputQuantity = shoppingProduct.getQuantity();
        int promotionItemsAvailableSize = calculateMaxCountOfPromotionItemsAvailable(shoppingProduct);
        return inputQuantity - promotionItemsAvailableSize;
    }

    public Receipt generateReceipt(final List<ShoppingProduct> shoppingProducts, final boolean hasMembershipBenefit) {
        List<Gift> gifts = getGifts(shoppingProducts);
        Calculator calculator = new Calculator(shoppingProducts, gifts);
        return calculator.calculatePrice(hasMembershipBenefit);
    }

    private List<Gift> getGifts(final List<ShoppingProduct> shoppingProducts) {
        return shoppingProducts.stream()
                .filter(this::isPromotionProduct)
                .map(product -> new Gift(product.getName(), calculateNumberOfGift(product), product.getPrice(),
                        items.getPromotionBundleSize(product.getName(), promotions)))
                .toList();
    }

    private int calculateNumberOfGift(final ShoppingProduct shoppingProduct) {
        int promotionItemsAvailableSize = calculateMaxCountOfPromotionItemsAvailable(shoppingProduct);
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        int maxStockBasedPromotionApplied = promotionItemsAvailableSize / promotionGroupSize;

        int inputPromotionAppliedCount = shoppingProduct.getQuantity() / promotionGroupSize;

        return Math.min(maxStockBasedPromotionApplied, inputPromotionAppliedCount);
    }

    public boolean isPromotionProduct(final ShoppingProduct shoppingProduct) {
        if (items.isExistPromotionProduct(shoppingProduct.getName())) {
            String promotionName = items.findPromotionNameOfItem(shoppingProduct.getName());
            Promotion promotion = promotions.findPromotionByName(promotionName);
            return promotion.isBetweenPromotionDuration(shoppingProduct.getDate());
        }
        return false;
    }


     */

    public List<Item> getItems() {
        return items.getItems();
    }

    /*
    private int calculateMaxCountOfPromotionItemsAvailable(final ShoppingProduct shoppingProduct) {
        int remainingStock = items.checkRemainingPromotionStock(shoppingProduct.getName());
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        int fullPromotionGroupCount = remainingStock / promotionGroupSize;
        return fullPromotionGroupCount * promotionGroupSize;
    }

    private boolean isLessThanPromotionRemainingStock(final ShoppingProduct shoppingProduct) {
        return items.checkRemainingPromotionStock(shoppingProduct.getName()) > shoppingProduct.getQuantity();
    }

    private boolean isLessThanPromotionStandard(final ShoppingProduct shoppingProduct) {
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        return shoppingProduct.getQuantity() % promotionGroupSize != EXACT_MATCH;
    }


     */
}
