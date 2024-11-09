package store.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    @DisplayName("멤버십 회원 테스트")
    void isMemberOfConvenience() {
        //given
        Customer customer = new Customer(MemberShipType.MEMBERSHIP_MEMBER);

        // given & when
        assertThat(customer.hasMembership()).isTrue();
    }

    @Test
    @DisplayName("일반 회원 테스트")
    void isNotMemberOfConvenience() {
        //given
        Customer customer = new Customer(MemberShipType.REGULAR_MEMBER);

        // given & when
        assertThat(customer.hasMembership()).isFalse();
    }
}
