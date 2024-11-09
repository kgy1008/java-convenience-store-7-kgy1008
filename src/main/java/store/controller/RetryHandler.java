package store.controller;

import java.util.function.Supplier;
import store.io.view.OutputView;

public class RetryHandler {

    private final OutputView outputView;

    RetryHandler(final OutputView outputView) {
        this.outputView = outputView;
    }

    <T> T retryTemplate(final Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    void retryTemplate(final Runnable action) {
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
