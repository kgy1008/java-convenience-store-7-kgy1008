package store.domain.user;

public class Customer {

    private final MemberShipType memberShipType;

    public Customer() {
        memberShipType = setMemberShipType();
    }

    public boolean hasMembership() {
        return this.memberShipType == MemberShipType.MEMBERSHIP_MEMBER;
    }

    private MemberShipType setMemberShipType() {
        return MemberShipType.MEMBERSHIP_MEMBER;
    }
}
