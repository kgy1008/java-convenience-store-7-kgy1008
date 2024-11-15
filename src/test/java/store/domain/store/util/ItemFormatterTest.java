package store.domain.store.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static store.common.ErrorMessage.CONFLICT_EXCEPTION;
import static store.common.ErrorMessage.EXCEED_QUANTITY;
import static store.common.ErrorMessage.NOT_FOUND;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.store.item.BasicItem;
import store.domain.store.item.Items;
import store.domain.store.item.PromotionItem;
import store.domain.user.ShoppingItem;

class ItemFormatterTest {

    private final ItemFormatter itemFormatter = new ItemFormatter();
    private final Items items = new Items(List.of(
            new PromotionItem("콜라", 1800, 10, "탄산2+1"),
            new BasicItem("콜라", 1800, 5),
            new BasicItem("새우깡", 3000, 15)
    ));

    @Test
    @DisplayName("중복된 상품이 입력으로 들어왔을 경우, 예외 발생")
    void validateDuplicated() {
        String input = "[콜라-3],[콜라-2]";

        assertThatThrownBy(() -> itemFormatter.convertStringToItem(input, items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(CONFLICT_EXCEPTION.getMessage());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 상품이 입력으로 들어왔을 경우, 예외 발생")
    @ValueSource(strings = {"[사이다-2]", "[새우-1]", "[콜라-3],[사이다-2]"})
    void validateNotExistProduct(final String input) {
        assertThatThrownBy(() -> itemFormatter.convertStringToItem(input, items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(NOT_FOUND.getMessage());
    }

    @ParameterizedTest
    @DisplayName("입력된 재품 수량이 재고를 초과할 경우, 예외 발생")
    @ValueSource(strings = {"[콜라-16]", "[새우깡-20]", "[새우깡-1],[콜라-16]"})
    void validateIsExceedQuantity(final String input) {
        assertThatThrownBy(() -> itemFormatter.convertStringToItem(input, items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EXCEED_QUANTITY.getMessage());
    }

    @ParameterizedTest
    @DisplayName("유효하지 않는 수량이 입력값으로 들어올 경우, 예외 발생")
    @ValueSource(strings = {"[콜라-0]", "[콜라--1]", "[콜라-s]"})
    void validateNotPositiveQuantity(final String input) {
        assertThatThrownBy(() -> itemFormatter.convertStringToItem(input, items))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("정상 입력 테스트")
    @ValueSource(strings = {"[콜라-15]", "[새우깡-15]", "[콜라-15],[새우깡-15]"})
    void validShoppingItem(final String input) {
        List<ShoppingItem> shoppingItems = itemFormatter.convertStringToItem(input, items);

        assertNotNull(shoppingItems);
    }
}
