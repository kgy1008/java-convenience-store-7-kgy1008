package store.domain.store.item;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicItemTest {

    @Test
    @DisplayName("재고 차감 테스트")
    void decreaseQuantity() {
        // given
        BasicItem basicItem = new BasicItem("물", 500, 10);
        // when
        basicItem.decreaseQuantity(5);
        // then
        assertEquals(5, basicItem.getQuantity());
    }

    @Test
    @DisplayName("재고가 0개 미만일때, 예외 발생")
    void invalidQuantity() {
        // given
        BasicItem basicItem = new BasicItem("물", 500, 2);
        // when & then
        assertThatThrownBy(() -> basicItem.decreaseQuantity(5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("재고 증가 테스트")
    void increaseQuantity() {
        // given
        BasicItem basicItem = new BasicItem("물", 500, 10);
        // when
        basicItem.increaseQuantity(1);
        // then
        assertEquals(11, basicItem.getQuantity());
    }
}
