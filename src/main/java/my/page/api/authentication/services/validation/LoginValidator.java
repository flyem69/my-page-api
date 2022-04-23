package my.page.api.authentication.services.validation;

import my.page.api.authentication.enumeration.AuthenticationRegex;
import my.page.api.authentication.enumeration.validation.UserDataValidationResult;
import my.page.api.authentication.models.UserData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
public class LoginValidator implements UserDataValidator {

    private final List<Map.Entry<Predicate<UserData>, UserDataValidationResult>> validations =
            Collections.singletonList(Map.entry(userData -> !userData.getEmail()
                                                                     .matches(AuthenticationRegex.EMAIL),
                                                UserDataValidationResult.INVALID_EMAIL));

    @Override
    public @NotNull UserDataValidationResult validate(@NotNull UserData userData) {
        if (userData.getEmail() == null || userData.getPassword() == null) {
            return UserDataValidationResult.INVALID_DATA_FORMAT;
        }
        return execute(userData, validations);
    }
}
