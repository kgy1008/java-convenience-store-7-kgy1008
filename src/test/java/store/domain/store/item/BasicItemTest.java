package store.domain.store.item;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicItemTest {

    private BasicItem basicItem;

    @BeforeEach
    void setUp() {
        basicItem = new BasicItem("물", 500, 10);
    }

    @Test
    @DisplayName("재고 차감 테스트")
    void decreaseQuantity() {
        // when
        basicItem.decreaseQuantity(5);
        // then
        assertEquals(5, basicItem.getQuantity());
    }

    @Test
    @DisplayName("재고가 0개 미만일때, 예외 발생")
    void invalidQuantity() {
        // when & then
        assertThatThrownBy(() -> basicItem.decreaseQuantity(11))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("재고 증가 테스트")
    void increaseQuantity() {
        // when
        basicItem.increaseQuantity(1);
        // then
        assertEquals(11, basicItem.getQuantity());
    }
}
