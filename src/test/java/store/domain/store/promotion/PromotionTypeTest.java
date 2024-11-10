package store.domain.store.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTypeTest {

    @Test
    @DisplayName("프로모션 타입 찾는 테스트1")
    void findOnePlusOnePromotionType() {
        // given
        int buyCount = 1;
        int freeToBuy = 1;
        // when
        PromotionType promotionType = PromotionType.findPromotionType(buyCount, freeToBuy);
        // then
        assertThat(promotionType).isEqualTo(PromotionType.ONE_PLUS_ONE);
    }

    @Test
    @DisplayName("프로모션 타입 찾는 테스트2")
    void findTwoPlusOnePromotionType() {
        // given
        int buyCount = 2;
        int freeToBuy = 1;
        // when
        PromotionType promotionType = PromotionType.findPromotionType(buyCount, freeToBuy);
        // then
        assertThat(promotionType).isEqualTo(PromotionType.TWO_PLUS_ONE);
    }

    @Test
    @DisplayName("프로모션 묶음 개수 테스트")
    void getPromotionBundleSize() {
        // given
        PromotionType promotionType = PromotionType.TWO_PLUS_ONE;
        // when
        int size = PromotionType.getPromotionBundleSize(promotionType);
        // then
        assertThat(size).isEqualTo(3);
    }
}
