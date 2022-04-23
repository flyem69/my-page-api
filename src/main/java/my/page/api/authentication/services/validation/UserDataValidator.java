package my.page.api.authentication.services.validation;

import my.page.api.authentication.enumeration.validation.UserDataValidationResult;
import my.page.api.authentication.models.UserData;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@FunctionalInterface
public interface UserDataValidator {

    @NotNull UserDataValidationResult validate(@NotNull UserData userData);

    @Transactional(readOnly = true)
    default @NotNull UserDataValidationResult execute(@NotNull UserData userData,
                                                      @NotNull List<Map.Entry<Predicate<UserData>, UserDataValidationResult>> validations) {

        return validations.stream()
                          .filter(validation -> validation.getKey()
                                                          .test(userData))
                          .map(Map.Entry::getValue)
                          .findFirst()
                          .orElse(UserDataValidationResult.OK);
    }
}
