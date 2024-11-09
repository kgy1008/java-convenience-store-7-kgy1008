package store.domain.store;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.user.ShoppingProduct;

class ConvenienceTest {

    private final Convenience convenience = new Convenience();

    @Test
    @DisplayName("프로모션 재고가 부족한 상황 테스트")
    void checkPromotionRemainingStock() {
        // given
        ShoppingProduct shoppingProduct = new ShoppingProduct("콜라", 15);
        boolean expected = true;

        // when & then
        assertThat(convenience.isGreaterThanPromotionRemaingStock(shoppingProduct)).isEqualTo(expected);
    }

    @Test
    @DisplayName("프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 상황 테스트")
    void checkAdditionalPromotionBenefit() {
        // given
        ShoppingProduct shoppingProduct = new ShoppingProduct("초코바", 1);
        boolean expected = true;

        // when & then
        assertThat(convenience.canReceiveAdditionalBenefit(shoppingProduct)).isEqualTo(expected);
    }

    @Test
    @DisplayName("프로모션 혜택 없이 결제해야 하는 경우, 정가로 결제할 수량 개수 테스트")
    void checkCountOfProductWithoutPromotion() {
        // given
        ShoppingProduct shoppingProduct = new ShoppingProduct("초코바", 7);
        int expected = 3;

        assertThat(convenience.getItemCountWithoutPromotion(shoppingProduct)).isEqualTo(expected);
    }

}
