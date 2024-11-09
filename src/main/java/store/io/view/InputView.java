package store.io.view;

import static store.common.ErrorMessage.INVALID_FORMAT;
import static store.common.ErrorMessage.INVALID_INPUT;

import camp.nextstep.edu.missionutils.Console;
import store.common.exception.AppException;

public class InputView {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String PURCHASE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String PROMOTION_WARNING_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%s";
    private static final String PROMOTION_BENEFIT_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%s";
    private static final String DELIMITER = ",";
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";
    private static final String DETAIL_DELIMITER = "-";

    public String inputShoppingItems() {
        System.out.println(PURCHASE_MESSAGE);
        String input = Console.readLine();
        validate(input);
        return input;
    }

    public String askForPurchaseWithWarning(final String name, final int quantity) {
        System.out.print(NEW_LINE);
        System.out.printf(PROMOTION_WARNING_MESSAGE, name, quantity, NEW_LINE);
        return Console.readLine();
    }

    public String askForBenefitWithAdditional(final String name) {
        System.out.print(NEW_LINE);
        System.out.printf(PROMOTION_BENEFIT_MESSAGE, name, NEW_LINE);
        return Console.readLine();
    }

    private void validate(final String input) {
        validateBlank(input);
        String[] items = input.split(DELIMITER);
        for (String item : items) {
            validateFormat(item);
        }
    }
    
    private void validateBlank(final String input) {
        if (input == null || input.isBlank()) {
            throw new AppException(INVALID_INPUT.getMessage());
        }
    }

    private void validateFormat(final String input) {
        if (input.contains(PREFIX) && input.contains(SUFFIX) && input.contains(DETAIL_DELIMITER)) {
            return;
        }
        throw new AppException(INVALID_FORMAT.getMessage());
    }
}
