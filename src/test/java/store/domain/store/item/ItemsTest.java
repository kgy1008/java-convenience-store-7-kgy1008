package store.domain.store.item;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.io.provider.ProductFileReader;

class ItemsTest {

    private final ProductFileReader productFileReader = new ProductFileReader();
    private final Items items = productFileReader.getItems();

    @Test
    @DisplayName("전체 남은 재고 확인")
    void checkRemainingStock() {
        // given
        String ItemName = "콜라";
        // when
        int stock = items.checkRemainingStock(ItemName);
        int expected = 20;
        // then
        assertThat(stock).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("프로모션 상품인지 확인")
    @CsvSource({
            "콜라, true",
            "물, false"
    })
    void checkIsPromotionStock(final String name, final boolean expected) {
        // when
        boolean result = items.isPromotionItem(name);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("프로모션 상품 남은 재고 확인")
    void findRemainingStockOfPromotionItem() {
        // given
        String ItemName = "콜라";
        // when
        int stock = items.checkRemainingPromotionStock(ItemName);
        int expected = 10;
        // then
        assertThat(stock).isEqualTo(expected);
    }
}
