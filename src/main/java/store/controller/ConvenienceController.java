package store.controller;

import static store.common.ErrorMessage.UNAUTHORIZED_EXCEPTION;
import static store.domain.store.KioskStatus.ON;

import java.util.List;
import store.common.exception.AppException;
import store.domain.store.Convenience;
import store.domain.store.KioskStatus;
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

    public void run() {
        KioskStatus kioskStatus = ON;
        while (kioskStatus == ON) {
            purchase();
            kioskStatus = askForBuyMore();
        }
    }

    private void purchase() {
        retryHandler.retryTemplate(this::displayProduct);
        List<ShoppingProduct> shoppingProducts = retryHandler.retryTemplate(this::tryToBuy);
        checkPromotionPolicy(shoppingProducts);
        boolean receiveMembershipBenefit = checkMemberShipBenefit();
        calculatePrice(receiveMembershipBenefit);
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
            if (convenience.isPromotionNotApplicableToAllItems(shoppingProduct)) {
                handlePromotionStockShortage(shoppingProduct);
            }
            if (convenience.canReceiveAdditionalBenefit(shoppingProduct)) {
                handleAdditionalBenefit(shoppingProduct);
            }
        }
    }

    private void handlePromotionStockShortage(final ShoppingProduct shoppingProduct) {
        int itemsWithoutPromotionCount = convenience.calculateItemCountWithoutPromotion(shoppingProduct);
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

    private void calculatePrice(final boolean hasMembershipBenefit) {
        List<ShoppingProduct> shoppingProducts = customer.getCart();
        convenience.calculatePrice(shoppingProducts);
    }

    private KioskStatus askForBuyMore() {
        UserResponse userResponse = inputView.askForBuyMore();
        return KioskStatus.turnOnOrOff(userResponse);
    }
}
