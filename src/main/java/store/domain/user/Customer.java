package store.domain.user;

public class Customer {

    private final MemberShipType memberShipType;

    public Customer(final MemberShipType memberShipType) {
        this.memberShipType = memberShipType;
    }

    public boolean hasMembership() {
        return this.memberShipType == MemberShipType.MEMBERSHIP_MEMBER;
    }
}
