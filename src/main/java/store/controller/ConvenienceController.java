package store.controller;

import static store.domain.store.KioskStatus.ON;

import java.util.function.Supplier;
import store.domain.store.Cashier;
import store.domain.store.Convenience;
import store.domain.store.KioskStatus;
import store.domain.store.item.Items;
import store.domain.user.Customer;
import store.domain.user.MemberShipType;
import store.domain.user.ShoppingProducts;
import store.domain.user.UserResponse;
import store.dto.ItemStatus;
import store.io.view.InputView;
import store.io.view.OutputView;

public class ConvenienceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Convenience convenience;
    private final Cashier cashier;
    private final Customer customer;

    public ConvenienceController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.convenience = new Convenience();
        this.cashier = new Cashier(convenience);
        this.customer = new Customer(MemberShipType.MEMBERSHIP_MEMBER);
    }

    public void run() {
        KioskStatus kioskStatus = ON;
        while (kioskStatus == ON) {
            purchase();
            kioskStatus = askForBuyMore();
        }
    }

    private void purchase() {
        retryTemplate(this::displayProduct);
        ShoppingProducts products = pickProductsToBuy();
        classifyProducts(products);
        //checkPromotionPolicy();
        /*
        boolean receiveMembershipBenefit = checkMemberShipBenefit();
        Receipt receipt = calculatePrice(receiveMembershipBenefit);
        displayReceipt(receipt);

         */
    }

    private void displayProduct() {
        outputView.printWelcomeMessage();
        Items items = convenience.getItems();
        ItemStatus itemStatus = new ItemStatus(items.getItems());
        outputView.printProducts(itemStatus);
    }

    private ShoppingProducts pickProductsToBuy() {
        return retryTemplate(() -> {
            String shoppingItems = inputView.inputShoppingItems();
            return convenience.getShoppingItemsFromUser(shoppingItems);
        });
    }

    private void classifyProducts(final ShoppingProducts shoppingProducts) {
        cashier.receiveAndClassifyItems(shoppingProducts);
    }

    /*

    private void checkPromotionPolicy() {
        for (ShoppingProduct shoppingProduct : shoppingProducts.getProducts()) {
            if (convenience.isPromotionApplicableToday(shoppingProduct)) {
                if (convenience.isPromotionNotApplicableToAllItems(shoppingProduct)) {
                    handlePromotionStockShortage(shoppingProduct);
                }
                if (convenience.canReceiveAdditionalBenefit(shoppingProduct)) {
                    handleAdditionalBenefit(shoppingProduct);
                }
            }
        }
    }

    private void handlePromotionStockShortage(final ShoppingProduct shoppingProduct) {
        int itemsWithoutPromotionCount = convenience.calculateItemCountWithoutPromotion(shoppingProduct);
        UserResponse userResponse = retryTemplate(() ->
                inputView.askForPurchaseWithWarning(shoppingProduct.getName(), itemsWithoutPromotionCount)
        );
        if (userResponse == UserResponse.NO) {
            customer.removeFromCart(shoppingProduct, itemsWithoutPromotionCount);
        }
    }

    private void handleAdditionalBenefit(final ShoppingProduct shoppingProduct) {
        UserResponse userResponse = retryTemplate(() ->
                inputView.askForBenefitWithAdditional(shoppingProduct.getName())
        );
        if (userResponse == UserResponse.YES) {
            customer.addCart(shoppingProduct);
        }
    }

    /*
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

    private Receipt calculatePrice(final boolean hasMembershipBenefit) {
        List<ShoppingProduct> shoppingProducts = customer.getCart();
        return convenience.generateReceipt(shoppingProducts, hasMembershipBenefit);
    }

    private void displayReceipt(final Receipt receipt) {
        outputView.printReceipt(receipt);
    }
     */

    private KioskStatus askForBuyMore() {
        UserResponse userResponse = inputView.askForBuyMore();
        return KioskStatus.turnOnOrOff(userResponse);
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
