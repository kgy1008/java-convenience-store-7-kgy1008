package store.domain.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.store.item.BasicItem;
import store.domain.store.item.PromotionItem;
import store.dto.FreeItem;

class CalculatorTest {

    private final List<FreeItem> freeItems = List.of(
            new FreeItem("콜라", 1, 1000, 3)
    );
    private final Calculator calculator = new Calculator(freeItems);
    private final List<BasicItem> basicItems = new ArrayList<>();
    private final List<PromotionItem> promotionItems = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // given
        basicItems.add(new BasicItem("에너지바", 2000, 3));
        promotionItems.addAll(List.of(
                new PromotionItem("콜라", 1000, 3, "탄산2+1"),
                new PromotionItem("오렌지주스", 1800, 1, "MD추천상품")
        ));
    }

    @Test
    @DisplayName("혜택 없이 지불해야하는 원금 계산 테스트")
    void calculateTotalPrice() {
        int expected = 6000 + 3000 + 1800;
        // when
        int result = calculator.calculateTotalPrice(basicItems, promotionItems);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("프로모션 혜택 금액 계산 테스트")
    void calculatePromotionDiscountPrice() {
        int expected = 1000;
        // when
        int result = calculator.calculatePromotionDiscountPrice();
        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("멤버십 혜택 금액 계산 테스트")
    @CsvSource({"true, 2340", "false, 0"})
    void calculateMemberShipDiscountPrice(final boolean isMemberShip, final int expected) {
        // when
        int totalPrice = calculator.calculateTotalPrice(basicItems, promotionItems);
        int result = calculator.calculateMemberShipDiscountPrice(isMemberShip, totalPrice);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateTotalCount() {
        int expected = 7;
        // when
        int result = calculator.calculateTotalCount(basicItems, promotionItems);
        // then
        assertThat(result).isEqualTo(expected);
    }
}
