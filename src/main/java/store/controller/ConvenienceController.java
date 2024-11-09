package store.controller;

import static store.common.ErrorMessage.UNAUTHORIZED_EXCEPTION;

import java.util.List;
import store.common.exception.AppException;
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
    private final RetryHandler retryHandler;
    private final Convenience convenience;
    private final Customer customer;

    public ConvenienceController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.retryHandler = new RetryHandler(outputView);
        this.convenience = new Convenience();
        this.customer = new Customer();
    }

    public void purchase() {
        retryHandler.retryTemplate(this::displayProduct);
        List<ShoppingProduct> shoppingProducts = retryHandler.retryTemplate(this::tryToBuy);
        checkPromotionPolicy(shoppingProducts);
        boolean receiveMembershipBenefit = checkMemberShipBenefit();
    }

    private void displayProduct() {
        outputView.printWelcomeMessage();
        List<Item> items = convenience.getItems();
        ItemStatus itemStatus = new ItemStatus(items);
        outputView.printProducts(itemStatus);
    }

    private List<ShoppingProduct> tryToBuy() {
        String shoppingItems = retryHandler.retryTemplate(inputView::inputShoppingItems);
        List<ShoppingProduct> shoppingProducts = convenience.checkPurchaseItems(shoppingItems);
        customer.purchase(shoppingProducts);
        return shoppingProducts;
    }

    private void checkPromotionPolicy(final List<ShoppingProduct> shoppingProducts) {
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
        UserResponse userResponse = retryHandler.retryTemplate(() ->
                inputView.askForPurchaseWithWarning(shoppingProduct.getName(), itemsWithoutPromotionCount)
        );
        if (userResponse == UserResponse.NO) {
            customer.removeFromCart(shoppingProduct, itemsWithoutPromotionCount);
        }
    }

    private void handleAdditionalBenefit(final ShoppingProduct shoppingProduct) {
        UserResponse userResponse = retryHandler.retryTemplate(() ->
                inputView.askForBenefitWithAdditional(shoppingProduct.getName())
        );
        if (userResponse == UserResponse.YES) {
            customer.addCart(shoppingProduct);
        }
    }

    private boolean checkMemberShipBenefit() {
        return retryHandler.retryTemplate(() -> {
            UserResponse userResponse = inputView.askForGetMembershipBenefit();
            if (userResponse == UserResponse.YES) {
                return verifyMembership();
            }
            return false;
        });
    }

    private boolean verifyMembership() {
        if (!customer.hasMembership()) {
            throw new AppException(UNAUTHORIZED_EXCEPTION.getMessage());
        }
        return true;
    }
}
