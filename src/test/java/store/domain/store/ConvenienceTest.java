package store.domain.store;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.store.item.PromotionItem;

class ConvenienceTest {

    private final Convenience convenience = new Convenience();

    @Test
    @DisplayName("프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 상황 테스트")
    void checkAdditionalPromotionBenefit() {
        // given
        PromotionItem promotionItem = new PromotionItem("초코바", 1200, 1, "MD추천상품");
        boolean expected = true;
        // when & then
        assertThat(convenience.canReceiveAdditionalBenefit(promotionItem)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("프로모션 혜택 없이 결제해야 하는 경우, 정가로 결제할 수량 개수 테스트")
    @CsvSource({
            "컵라면, 1700, MD추천상품, 2, 2",
            "콜라, 1000, 탄산2+1, 10, 1",
            "콜라, 1000, 탄산2+1, 11, 2",
            "콜라, 1000, 탄산2+1, 12, 3"
    })
    void checkCountOfProductWithoutPromotion(final String productName, final int price, final String promotion,
                                             final int productCount, final int expected) {
        // given
        PromotionItem promotionItem = new PromotionItem(productName, price, productCount, promotion);
        // when & then
        assertThat(convenience.calculateItemCountWithoutPromotion(promotionItem)).isEqualTo(expected);
    }
}
