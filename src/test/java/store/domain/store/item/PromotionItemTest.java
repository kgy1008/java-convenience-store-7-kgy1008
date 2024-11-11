package store.domain.store.item;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionItemTest {

    private PromotionItem promotionItem;

    @BeforeEach
    void setUp() {
        // given
        promotionItem = new PromotionItem("감자칩", 1500, 5, "반짝할인");
    }

    @Test
    @DisplayName("재고 차감 테스트")
    void decreaseQuantity() {
        // when
        promotionItem.decreaseQuantity(5);
        // then
        assertEquals(0, promotionItem.getQuantity());
    }

    @Test
    @DisplayName("재고가 0개 미만일때, 예외 발생")
    void invalidQuantity() {
        // when & then
        assertThatThrownBy(() -> promotionItem.decreaseQuantity(6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("재고 증가 테스트")
    void increaseQuantity() {
        // when
        promotionItem.increaseQuantity(1);
        // then
        assertEquals(6, promotionItem.getQuantity());
    }
}
