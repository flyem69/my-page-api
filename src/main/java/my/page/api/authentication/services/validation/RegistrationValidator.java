package my.page.api.authentication.services.validation;

import my.page.api.authentication.dao.UserRepository;
import my.page.api.authentication.enumeration.AuthenticationRegex;
import my.page.api.authentication.enumeration.validation.UserDataValidationResult;
import my.page.api.authentication.models.UserData;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class RegistrationValidator implements UserDataValidator {

    @Autowired
    UserRepository userRepository;

    private final List<Map.Entry<Predicate<UserData>, UserDataValidationResult>> validations;

    public RegistrationValidator() {
        validations = new ArrayList<>();
        validations.add(Map.entry(userData -> !userData.getEmail()
                                                       .matches(AuthenticationRegex.EMAIL),
                                  UserDataValidationResult.INVALID_EMAIL));
        validations.add(Map.entry(userData -> !userData.getName()
                                                       .matches(AuthenticationRegex.NAME),
                                  UserDataValidationResult.INVALID_NAME));
        validations.add(Map.entry(userData -> !userData.getPassword()
                                                       .matches(AuthenticationRegex.PASSWORD),
                                  UserDataValidationResult.INVALID_PASSWORD));
        validations.add(Map.entry(userData -> userRepository.findByEmail(userData.getEmail()) != null,
                                  UserDataValidationResult.EMAIL_EXISTS));
        validations.add(Map.entry(userData -> userRepository.findByName(userData.getName()) != null,
                                  UserDataValidationResult.NAME_EXISTS));
    }

    @Override
    public @NotNull UserDataValidationResult validate(@NotNull UserData userData) {
        if (userData.getEmail() == null || userData.getName() == null || userData.getPassword() == null) {
            return UserDataValidationResult.INVALID_DATA_FORMAT;
        }
        return execute(userData, validations);
    }
}
