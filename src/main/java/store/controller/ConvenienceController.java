package store.controller;

import java.util.List;
import store.domain.store.Convenience;
import store.domain.store.item.Item;
import store.domain.user.Customer;
import store.domain.user.ShoppingProduct;
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
        displayProduct();
        tryToBuy();
    }

    private void displayProduct() {
        outputView.printWelcomeMessage();
        List<Item> items = convenience.getItems();
        ItemStatus itemStatus = new ItemStatus(items);
        outputView.printProducts(itemStatus);
    }

    private void tryToBuy() {
        String shoppingItems = inputView.inputShoppingItems();
        List<ShoppingProduct> shoppingProducts = convenience.checkPurchaseItems(shoppingItems);
        customer.purchase(shoppingProducts);
    }
}
