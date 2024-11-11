package store.config;

import store.controller.ConvenienceController;
import store.domain.store.Cashier;
import store.domain.store.Convenience;
import store.domain.user.Customer;
import store.domain.user.MemberShipType;
import store.io.view.InputView;
import store.io.view.OutputView;

public class AppConfig {

    public ConvenienceController convenienceController() {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Convenience convenience = new Convenience();
        Cashier cashier = new Cashier(convenience);
        Customer customer = new Customer(MemberShipType.MEMBERSHIP_MEMBER);

        return new ConvenienceController(inputView, outputView, convenience, cashier, customer);
    }
}

