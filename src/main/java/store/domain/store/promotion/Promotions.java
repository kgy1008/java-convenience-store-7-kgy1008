package store.domain.store.promotion;

import static store.common.ErrorMessage.INVALID_INPUT;

import java.util.Set;
import store.common.exception.AppException;

public class Promotions {

    private final Set<Promotion> promotions;

    public Promotions(final Set<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion findPromotionByName(final String name) {
        return promotions.stream()
                .filter(promotion -> promotion.isEqual(name))
                .findFirst()
                .orElseThrow(() -> new AppException(INVALID_INPUT.getMessage()));
    }
}
