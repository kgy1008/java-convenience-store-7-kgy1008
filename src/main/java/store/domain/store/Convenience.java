package store.domain.store;

import java.util.List;
import store.domain.store.item.Items;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.Promotions;
import store.domain.store.util.ProductFormatter;
import store.domain.user.ShoppingProduct;
import store.domain.user.ShoppingProducts;

public class Convenience {

    private final Initializer initializer;
    private final Promotions promotions;
    private final Items items;

    public Convenience() {
        this.initializer = new Initializer();
        this.promotions = initializer.initPromotions();
        this.items = initializer.initItems();
    }

    public ShoppingProducts getShoppingItemsFromUser(final String input) {
        ProductFormatter productFormatter = new ProductFormatter();
        List<ShoppingProduct> products = productFormatter.convertStringToItem(input, items);
        return new ShoppingProducts(products);
    }

    boolean isPromotionApplicableToday(final ShoppingProduct shoppingProduct) {
        if (items.isExistPromotionProduct(shoppingProduct.getName())) {
            String promotionName = items.getPromotionNameOfItem(shoppingProduct.getName());
            Promotion promotion = promotions.findPromotionByName(promotionName);
            return promotion.isBetweenPromotionDuration();
        }
        return false;
    }
     /*
    public boolean isPromotionNotApplicableToAllItems(final ShoppingProduct shoppingProduct) {
        return calculateMaxCountOfPromotionApplied(shoppingProduct) < shoppingProduct.getQuantity();
    }

    public boolean canReceiveAdditionalBenefit(final ShoppingProduct shoppingProduct) {
        return isLessThanPromotionRemainingStock(shoppingProduct) && isLessThanPromotionStandard(shoppingProduct);
    }

    private int calculateMaxCountOfPromotionApplied(final ShoppingProduct shoppingProduct) {
        int remainingStock = items.checkRemainingPromotionStock(shoppingProduct.getName());
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        int fullPromotionGroupCount = remainingStock / promotionGroupSize;
        return fullPromotionGroupCount * promotionGroupSize;
    }


    public Receipt generateReceipt(final List<ShoppingProduct> shoppingProducts, final boolean hasMembershipBenefit) {
        List<Gift> gifts = getGifts(shoppingProducts);
        Calculator calculator = new Calculator(shoppingProducts, gifts);
        return calculator.calculatePrice(hasMembershipBenefit);
    }




    public int calculateItemCountWithoutPromotion(final ShoppingProduct shoppingProduct) {
        int inputQuantity = shoppingProduct.getQuantity();
        int promotionItemsAvailableSize = calculateMaxCountOfPromotionAvailable(shoppingProduct);
        return inputQuantity - promotionItemsAvailableSize;
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


     */

    public Items getItems() {
        return items;
    }

    /*


    private boolean isLessThanPromotionRemainingStock(final ShoppingProduct shoppingProduct) {
        return items.checkRemainingPromotionStock(shoppingProduct.getName()) > shoppingProduct.getQuantity();
    }

    private boolean isLessThanPromotionStandard(final ShoppingProduct shoppingProduct) {
        int promotionGroupSize = items.getPromotionBundleSize(shoppingProduct.getName(), promotions);
        return shoppingProduct.getQuantity() % promotionGroupSize != EXACT_MATCH;
    }


     */
}
