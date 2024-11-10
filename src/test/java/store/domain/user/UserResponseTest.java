package store.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserResponseTest {

    @ParameterizedTest
    @DisplayName("잘못된 입력값이 들어오면 예외 발생")
    @NullAndEmptySource
    @ValueSource(strings = {"$", "sdf", "1"})
    void invalidInput(final String input) {
        assertThatThrownBy(() -> UserResponse.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("유효한 입력값(YES)일 때 반환 테스트")
    @ValueSource(strings = {"y", "Y"})
    void validYesInput(final String input) {
        UserResponse expectedResponse = UserResponse.YES;
        // when
        UserResponse userResponse = UserResponse.from(input);
        // then
        assertThat(userResponse).isEqualTo(expectedResponse);
    }

    @ParameterizedTest
    @DisplayName("유효한 입력값(NO)일 때 반환 테스트")
    @ValueSource(strings = {"n", "N"})
    void validNoInput(final String input) {
        UserResponse expectedResponse = UserResponse.NO;
        // when
        UserResponse userResponse = UserResponse.from(input);
        // then
        assertThat(userResponse).isEqualTo(expectedResponse);
    }
}
