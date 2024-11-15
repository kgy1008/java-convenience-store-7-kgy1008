package store.domain.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.store.item.PromotionItem;

class CashierTest {

    private final Convenience convenience = new Convenience();
    private final Cashier cashier = new Cashier(convenience);

    @Test
    @DisplayName("프로모션 재고를 넘어서는 쇼핑 목록들 식별 테스트")
    void findExceedingPromotionStockItems() {
        // given
        cashier.getShoppingItemsFromUser("[콜라-11],[초코바-7],[물-1]");

        // when
        List<PromotionItem> promotionItems = cashier.getExceedingPromotionItems();

        // then
        assertThat(promotionItems)
                .hasSize(2)
                .extracting(PromotionItem::getName)
                .contains("콜라", "초코바");
    }

    @Test
    @DisplayName("프로모션 혜택보다 적게 가져온 쇼핑 목록들 식별 테스트")
    void findShortagePromotionItems() {
        // given
        cashier.getShoppingItemsFromUser("[콜라-2],[비타민워터-1],[초코바-1]");
        // when
        List<PromotionItem> promotionItems = cashier.getShortagePromotionItems();
        // then
        assertThat(promotionItems)
                .hasSize(2)
                .extracting(PromotionItem::getName)
                .contains("콜라", "초코바");
    }

    @Test
    @DisplayName("장바구니에 올바르게 추가되었는지 테스트")
    void increasePromotionItemCount() {
        // given
        cashier.getShoppingItemsFromUser("[콜라-2],[에너지바-5]");
        // when
        List<PromotionItem> promotionItems = cashier.getShortagePromotionItems();
        PromotionItem promotionItem = promotionItems.getFirst();
        cashier.addPromotionItemFromCart(promotionItem);
        // then
        assertThat(promotionItem.getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("장바구니에서 들어온 개수만큼 올바르게 제거되었는지 테스트")
    void decreasePromotionItemCount() {
        // given
        cashier.getShoppingItemsFromUser("[콜라-11],[물-1]");
        // when
        List<PromotionItem> promotionItems = cashier.getExceedingPromotionItems();
        PromotionItem promotionItem = promotionItems.getFirst();
        cashier.removePromotionItemFromCart(promotionItem, 3);
        // then
        assertThat(promotionItem.getQuantity()).isEqualTo(8);
    }

    @Test
    @DisplayName("장바구니가 비어있는지 않은지 확인 테스트")
    void checkCartIsEmpty() {
        // given
        cashier.getShoppingItemsFromUser("[컵라면-1]");
        // when
        boolean result = cashier.isCartNotEmpty();
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("사용자가 구매후, 편의점 물품 재고 업데이트 테스트")
    void finishPayment() {
        // given
        cashier.getShoppingItemsFromUser("[콜라-3]");
        // when
        cashier.finishPayment();
        // then
        assertThat(convenience.getItems().findItemByName("콜라").getQuantity()).isEqualTo(7);
    }
}
