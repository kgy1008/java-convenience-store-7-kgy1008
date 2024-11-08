package store.io.view;

import static store.common.ErrorMessage.INVALID_FORMAT;

import camp.nextstep.edu.missionutils.Console;
import store.common.exception.AppException;

public class InputView {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String PURCHASE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String PROMOTION_WARNING_MESSAGE =
            NEW_LINE + "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)" + NEW_LINE;
    private static final String DELIMITER = ",";
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";
    private static final String DETAIL_DELIMITER = "-";

    public String inputShoppingItems() {
        System.out.println(NEW_LINE + PURCHASE_MESSAGE);
        String input = Console.readLine();
        validate(input);
        return input;
    }

    public String askForPurchaseWithWarning(final String name, final int quantity) {
        System.out.printf(PROMOTION_WARNING_MESSAGE, name, quantity);
        return Console.readLine();
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
