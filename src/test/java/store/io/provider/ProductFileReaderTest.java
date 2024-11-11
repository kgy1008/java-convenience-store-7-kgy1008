package store.io.provider;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.store.item.Items;

class ProductFileReaderTest {

    private final ProductFileReader productFileReader = new ProductFileReader();

    @Test
    @DisplayName("물품 파일 읽기 테스트")
    void getItems() {
        // when
        Items items = productFileReader.getItems();
        // then
        assertThat(items.getItems()).hasSize(18);
    }
}
