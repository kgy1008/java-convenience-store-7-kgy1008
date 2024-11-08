package store.domain.store.promotion;

import static store.common.ErrorMessage.NOT_FOUND;

import java.util.Set;
import store.common.exception.AppException;

public class Promotions {

    private final Set<Promotion> promotions;

    public Promotions(final Set<Promotion> promotions) {
        this.promotions = promotions;
    }

    public boolean isNotContain(final String name) {
        return promotions.stream()
                .noneMatch(promotion -> promotion.isEqual(name));
    }


    public Promotion findPromotionByName(final String name) {
        return promotions.stream().filter(promotion -> promotion.isEqual(name)).findFirst()
                .orElseThrow(() -> new AppException(NOT_FOUND.getMessage()));
    }
}
