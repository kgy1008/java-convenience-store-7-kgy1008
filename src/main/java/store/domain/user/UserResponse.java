package store.domain.user;

import static store.common.ErrorMessage.INVALID_INPUT;

import java.util.Arrays;
import store.common.exception.AppException;

public enum UserResponse {

    YES("Y"),
    NO("N");

    private final String description;

    UserResponse(final String description) {
        this.description = description;
    }

    public static UserResponse from(final String description) {
        return Arrays.stream(values())
                .filter(response -> response.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(
                        () -> new AppException(INVALID_INPUT.getMessage()));
    }
}
