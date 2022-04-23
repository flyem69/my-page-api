package my.page.api.authentication.enumeration.validation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserDataValidationResult {
    EMAIL_EXISTS("EMAIL_EXISTS"),
    NAME_EXISTS("NAME_EXISTS"),
    INVALID_DATA_FORMAT("INVALID_DATA_FORMAT"),
    INVALID_EMAIL("INVALID_EMAIL"),
    INVALID_NAME("INVALID_NAME"),
    INVALID_EMAIL_OR_NAME("INVALID_EMAIL_OR_NAME"),
    INVALID_PASSWORD("INVALID_PASSWORD"),
    WRONG_CREDENTIALS("WRONG_CREDENTIALS"),
    OK(null);

    private final String name;

    @Override
    public @Nullable String toString() {
        return this.name;
    }
}
