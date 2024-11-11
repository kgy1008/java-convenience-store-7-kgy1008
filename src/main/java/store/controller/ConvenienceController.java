package store.controller;

import static store.common.ErrorMessage.UNAUTHORIZED_EXCEPTION;
import static store.domain.store.KioskStatus.ON;

import java.util.List;
import java.util.function.Supplier;
import store.common.exception.AppException;
import store.domain.store.Cashier;
import store.domain.store.Convenience;
import store.domain.store.KioskStatus;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;
import store.domain.user.Customer;
import store.domain.user.UserResponse;
import store.dto.ItemStatus;
import store.dto.Receipt;
import store.io.view.InputView;
import store.io.view.OutputView;

public class ConvenienceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Convenience convenience;
    private final Cashier cashier;
    private final Customer customer;

    public ConvenienceController(final InputView inputView, final OutputView outputView, final Convenience convenience,
                                 final Cashier cashier, final Customer customer) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.convenience = convenience;
        this.cashier = cashier;
        this.customer = customer;
    }

    public void run() {
        KioskStatus kioskStatus = ON;
        while (kioskStatus == ON) {
            reviewPromotionAndConfirmPurchaseFromUser();
            executePayment();
            updateItem();
            kioskStatus = askForBuyMore();
        }
    }

    private void reviewPromotionAndConfirmPurchaseFromUser() {
        retryTemplate(this::displayItems);
        pickItemsToBuy();
        checkPromotionPolicy();
    }

    private void displayItems() {
        outputView.printWelcomeMessage();
        Items items = convenience.getItems();
        ItemStatus itemStatus = new ItemStatus(items.getItems());
        outputView.printItems(itemStatus);
    }

    private void pickItemsToBuy() {
        retryTemplate(() -> {
            String shoppingItems = inputView.inputShoppingItems();
            cashier.getShoppingItemsFromUser(shoppingItems);
        });
    }

    private void checkPromotionPolicy() {
        manageWhenPromotionStockIsExceed();
        manageUserPromotionItemShortage();
    }

    private void manageWhenPromotionStockIsExceed() {
        List<PromotionItem> promotionItems = cashier.getExceedingPromotionItems();
        for (PromotionItem promotionItem : promotionItems) {
            handlePromotionStockShortage(promotionItem);
        }
    }

    private void handlePromotionStockShortage(final PromotionItem promotionItem) {
        int itemsWithoutPromotionCount = convenience.calculateItemCountWithoutPromotion(promotionItem);
        retryTemplate(() -> {
            UserResponse userResponse = inputView.askForPurchaseWithWarning(
                    promotionItem.getName(), itemsWithoutPromotionCount);
            if (userResponse == UserResponse.NO) {
                cashier.removePromotionItemFromCart(promotionItem, itemsWithoutPromotionCount);
            }
        });
    }

    private void manageUserPromotionItemShortage() {
        List<PromotionItem> promotionItems = cashier.getShortagePromotionItems();
        for (PromotionItem promotionItem : promotionItems) {
            handleAdditionalBenefit(promotionItem);
        }
    }

    private void handleAdditionalBenefit(final PromotionItem promotionItem) {
        UserResponse userResponse = retryTemplate(() ->
                inputView.askForBenefitWithAdditional(promotionItem.getName())
        );
        if (userResponse == UserResponse.YES) {
            cashier.addPromotionItemFromCart(promotionItem);
        }
    }

    private void executePayment() {
        if (cashier.isCartNotEmpty()) {
            boolean receiveMembershipBenefit = checkMemberShipBenefit();
            Receipt receipt = calculatePurchaseAmount(receiveMembershipBenefit);
            displayReceipt(receipt);
        }
    }

    private boolean checkMemberShipBenefit() {
        return retryTemplate(() -> {
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

    private Receipt calculatePurchaseAmount(final boolean hasMembershipBenefit) {
        return cashier.generateReceipt(hasMembershipBenefit);
    }

    private void displayReceipt(final Receipt receipt) {
        outputView.printReceipt(receipt);
    }

    private void updateItem() {
        cashier.finishPayment();
    }

    private KioskStatus askForBuyMore() {
        UserResponse userResponse = retryTemplate(inputView::askForBuyMore);
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
