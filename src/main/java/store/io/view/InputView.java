package store.io.view;

import static store.common.ErrorMessage.INVALID_FORMAT;

import camp.nextstep.edu.missionutils.Console;
import store.common.exception.AppException;

public class InputView {

    private static final String PURCHASE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String DELIMITER = ",";
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";
    private static final String DETAIL_DELIMITER = "-";
    private static final String NEW_LINE = System.lineSeparator();

    public String inputShoppingItems() {
        System.out.println(NEW_LINE + PURCHASE_MESSAGE);
        String input = Console.readLine();
        validate(input);
        return input;
    }

    private void validate(final String input) {
        String[] items = input.split(DELIMITER);
        for (String item : items) {
            validateFormat(item);
        }
    }

    private void validateFormat(final String input) {
        if (input.contains(PREFIX) && input.contains(SUFFIX) && input.contains(DETAIL_DELIMITER)) {
            return;
        }
        throw new AppException(INVALID_FORMAT.getMessage());
    }
}
