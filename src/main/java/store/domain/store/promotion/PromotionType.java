package store.domain.store.promotion;

public enum PromotionType {

    ONE_PLUS_ONE(1, 1),
    TWO_PLUS_ONE(2, 1);

    private final int buyCount;
    private final int freeToGet;

    PromotionType(int buyCount, int freeToGet) {
        this.buyCount = buyCount;
        this.freeToGet = freeToGet;
    }

    public static PromotionType findPromotionType(final int buyCount, final int freeToGet) {
        if (buyCount == 2 && freeToGet == 1) {
            return TWO_PLUS_ONE;
        }
        return ONE_PLUS_ONE;
    }

    static int getPromotionBundleSize(final PromotionType promotionType) {
        return promotionType.buyCount + promotionType.freeToGet;
    }
}
