package store.domain.store.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.io.provider.PromotionFileReader;

class PromotionsTest {

    private final PromotionFileReader promotionFileReader = new PromotionFileReader();
    private final Promotions promotions = promotionFileReader.getPromotions();

    @Test
    @DisplayName("존재하지 않는 프로모션 검색 시, 예외 발생")
    void findPromotionByInvalidName() {
        // given
        String promotionName = "우테코합격";

        // when & then
        assertThatThrownBy(() -> promotions.findPromotionByName(promotionName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("프로모션 검색 테스트")
    void findPromotionByValidName() {
        // given
        String promotionName = "반짝할인";

        // when
        Promotion promotion = promotions.findPromotionByName(promotionName);

        // then
        assertThat(promotion)
                .isNotNull()
                .satisfies(p -> assertThat(p.isEqual(promotionName)).isTrue());
    }
}
