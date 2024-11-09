package store.domain.store.promotion;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final PromotionType promotionType;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(final String name, final PromotionType promotionType, final LocalDate startDate,
                     final LocalDate endDate) {
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

    public boolean isBetweenPromotionDuration() {
        LocalDate present = LocalDate.now();
        return (present.isEqual(startDate) || present.isAfter(startDate)) &&
                (present.isEqual(endDate) || present.isBefore(endDate));
    }
}
