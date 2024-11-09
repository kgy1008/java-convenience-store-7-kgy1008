package store.domain.store;

import static store.common.ErrorMessage.INVALID_INPUT;
import static store.domain.user.UserResponse.NO;
import static store.domain.user.UserResponse.YES;

import java.util.Arrays;
import store.common.exception.AppException;
import store.domain.user.UserResponse;

public enum KioskStatus {

    ON(YES),
    OFF(NO);

    private final UserResponse userResponse;

    KioskStatus(final UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public static KioskStatus turnOnOrOff(final UserResponse userResponse) {
        return Arrays.stream(KioskStatus.values())
                .filter(kioskStatus -> kioskStatus.isEqual(userResponse))
                .findFirst()
                .orElseThrow(() -> new AppException(INVALID_INPUT.getMessage()));
    }

    private boolean isEqual(final UserResponse userResponse) {
        return this.userResponse == userResponse;
    }
}
