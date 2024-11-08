package store.domain.store.promotion;

public class NoneDiscountPolicy implements DiscountPolicy {

    @Override
    public String getPolicyName() {
        return "";
    }
}
