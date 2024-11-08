package store.domain.promotion;

import java.util.Set;

public class Promotions {

    private final Set<Promotion> promotions;

    public Promotions(final Set<Promotion> promotions) {
        this.promotions = promotions;
    }
}
