package store.domain.store.promotion;

import java.time.LocalDate;

public class Promotion implements DiscountPolicy {

    private final String name;
    private final PromotionType promotionType;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, PromotionType promotionType, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.promotionType = promotionType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isEqual(final String name) {
        return this.name.equals(name);
    }

    @Override
    public String getPolicyName() {
        return name;
    }
}
