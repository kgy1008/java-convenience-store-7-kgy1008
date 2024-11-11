package store.io.provider;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.store.promotion.Promotions;

class PromotionFileReaderTest {

    private final PromotionFileReader promotionFileReader = new PromotionFileReader();

    @Test
    @DisplayName("프로모션 파일 읽기 테스트")
    void getPromotions() {
        // when
        Promotions promotions = promotionFileReader.getPromotions();
        // then
        assertThat(promotions).isNotNull();
    }
}
