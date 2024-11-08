package store.domain.store.promotion;

public class NoneDiscountPolicy implements DiscountPolicy {

    private static final String NO_PROMOTION = "";
    private static final int ZERO = 0;

    @Override
    public String getPolicyName() {
        return NO_PROMOTION;
    }

    @Override
    public int getPromotionBundleSize() {
        return ZERO;
    }
}
