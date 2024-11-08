package store.controller;

import java.util.List;
import java.util.function.Supplier;
import store.domain.store.Convenience;
import store.domain.store.item.Item;
import store.domain.user.Customer;
import store.domain.user.ShoppingProduct;
import store.domain.user.UserResponse;
import store.dto.ItemStatus;
import store.io.view.InputView;
import store.io.view.OutputView;

public class ConvenienceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Convenience convenience;
    private final Customer customer;

    public ConvenienceController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.convenience = new Convenience();
        this.customer = new Customer();
    }

    public void start() {
        retryTemplate(this::displayProduct);
        List<ShoppingProduct> shoppingProducts = retryTemplate(this::tryToBuy);
        checkDiscountPolicy(shoppingProducts);
    }

    private void displayProduct() {
        outputView.printWelcomeMessage();
        List<Item> items = convenience.getItems();
        ItemStatus itemStatus = new ItemStatus(items);
        outputView.printProducts(itemStatus);
    }

    private List<ShoppingProduct> tryToBuy() {
        String shoppingItems = retryTemplate(inputView::inputShoppingItems);
        List<ShoppingProduct> shoppingProducts = convenience.checkPurchaseItems(shoppingItems);
        customer.purchase(shoppingProducts);
        return shoppingProducts;
    }

    private void checkDiscountPolicy(final List<ShoppingProduct> shoppingProducts) {
        for (ShoppingProduct shoppingProduct : shoppingProducts) {
            if (isPromotionStockExceeded(shoppingProduct)) {
                handlePromotionStockWarning(shoppingProduct);
            }

            if (isReceiveAdditionalBenefit(shoppingProduct)) {
                handleAdditionalBenefit(shoppingProduct);
            }
        }
    }

    private boolean isPromotionStockExceeded(final ShoppingProduct shoppingProduct) {
        return convenience.isGreaterThanPromotionRemaingStock(shoppingProduct);
    }

    private boolean isReceiveAdditionalBenefit(final ShoppingProduct shoppingProduct) {
        return convenience.canReceiveAdditionalBenefit(shoppingProduct);
    }

    private void handlePromotionStockWarning(final ShoppingProduct shoppingProduct) {
        int itemsWithoutPromotionCount = convenience.getItemCountWithoutPromotion(shoppingProduct);
        UserResponse userResponse = retryTemplate(() -> {
            String answer = inputView.askForPurchaseWithWarning(shoppingProduct.getName(), itemsWithoutPromotionCount);
            return UserResponse.from(answer);
        });

        if (userResponse == UserResponse.NO) {
            customer.removeFromCart(shoppingProduct, itemsWithoutPromotionCount);
        }
    }

    private void handleAdditionalBenefit(final ShoppingProduct shoppingProduct) {
        UserResponse userResponse = retryTemplate(() -> {
            String answer = inputView.askForBenefitWithAdditional(shoppingProduct.getName());
            return UserResponse.from(answer);
        });

        if (userResponse == UserResponse.YES) {
            customer.addCart(shoppingProduct);
        }
    }

    private <T> T retryTemplate(final Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void retryTemplate(final Runnable action) {
        while (true) {
            try {
                action.run();
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
