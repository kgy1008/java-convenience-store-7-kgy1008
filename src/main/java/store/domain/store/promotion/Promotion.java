package store.domain.store.promotion;

import java.time.LocalDate;

public class Promotion {

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

    public int getPromotionBundleSize() {
        return PromotionType.getPromotionBundleSize(promotionType);
    }

    public boolean isBetweenPromotionDuration(final LocalDate present) {
        return (present.isEqual(startDate) || present.isAfter(startDate)) &&
                (present.isEqual(endDate) || present.isBefore(endDate));
    }
}
