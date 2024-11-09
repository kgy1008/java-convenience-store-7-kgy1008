package store.domain.store;

import java.util.List;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.Promotions;
import store.domain.store.util.ProductFormatter;
import store.domain.user.ShoppingProduct;
import store.domain.user.ShoppingProducts;

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

    boolean isPromotionNotApplicableToAllItems(final PromotionItem promotionItem) {
        return calculateMaxCountOfPromotionApplied(promotionItem) < promotionItem.getQuantity();
    }

    private int calculateMaxCountOfPromotionApplied(final PromotionItem promotionItem) {
        int remainingStock = items.checkRemainingPromotionStock(promotionItem.getName());
        int promotionGroupSize = items.getPromotionBundleSize(promotionItem.getName(), promotions);
        int fullPromotionGroupCount = remainingStock / promotionGroupSize;
        return fullPromotionGroupCount * promotionGroupSize;
    }

    public int calculateItemCountWithoutPromotion(final PromotionItem promotionItem) {
        int inputQuantity = promotionItem.getQuantity();
        int promotionItemsAvailableSize = calculateMaxCountOfPromotionApplied(promotionItem);
        return inputQuantity - promotionItemsAvailableSize;
    }

    boolean canReceiveAdditionalBenefit(final PromotionItem promotionItem) {
        return isLessThanPromotionRemainingStock(promotionItem) && isLessThanPromotionStandard(promotionItem);
    }

    private boolean isLessThanPromotionRemainingStock(final PromotionItem promotionItem) {
        return items.checkRemainingPromotionStock(promotionItem.getName()) > promotionItem.getQuantity();
    }

    private boolean isLessThanPromotionStandard(final PromotionItem promotionItem) {
        int promotionGroupSize = items.getPromotionBundleSize(promotionItem.getName(), promotions);
        return (promotionItem.getQuantity() % promotionGroupSize) != EXACT_MATCH;
    }


    /*
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


     */

    public Items getItems() {
        return items;
    }
}
