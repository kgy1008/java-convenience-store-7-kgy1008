package store.controller;

import static store.domain.user.UserResponse.NO;

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

    private void checkDiscountPolicy(List<ShoppingProduct> shoppingProducts) {
        for (ShoppingProduct shoppingProduct : shoppingProducts) {
            if (convenience.isGreaterThanPromotionRemaingStock(shoppingProduct)) {
                int itemsWithoutPromotionCount = convenience.getItemCountWithoutPromotion(shoppingProduct);
                String answer = inputView.askForPurchaseWithWarning(shoppingProduct.getName(),
                        itemsWithoutPromotionCount);
                UserResponse userResponse = UserResponse.from(answer);
                if (userResponse == NO) {
                    customer.removeFromCart(shoppingProduct, itemsWithoutPromotionCount);
                }
            }
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
