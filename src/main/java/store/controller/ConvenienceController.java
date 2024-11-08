package store.controller;

import java.util.List;
import store.domain.store.Convenience;
import store.domain.store.item.Item;
import store.dto.ItemStatus;
import store.io.view.InputView;
import store.io.view.OutputView;

public class ConvenienceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Convenience convenience;

    public ConvenienceController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.convenience = new Convenience();
    }

    public void start() {
        displayProduct();
    }

    private void displayProduct() {
        outputView.printWelcomeMessage();
        List<Item> items = convenience.getItems();
        ItemStatus itemStatus = new ItemStatus(items);
        outputView.printProducts(itemStatus);
    }
}
