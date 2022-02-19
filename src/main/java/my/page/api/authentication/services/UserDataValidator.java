package my.page.api.authentication.services;

import my.page.api.authentication.models.LoginUserData;
import my.page.api.authentication.models.UserData;
import my.page.api.authentication.dao.UserRepository;
import my.page.api.authentication.enumeration.UserDataValidationResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static my.page.api.authentication.enumeration.UserDataValidationResult.*;
import static my.page.api.authentication.enumeration.UserDataValidationResult.OK;

@Service
public class UserDataValidator {

    @Autowired
    UserRepository userRepository;

    private static final String EMAIL_REGEX = "^\\S{1,64}@(\\S{1,64}\\.)*\\S{1,253}\\.\\S{1,63}$";
    private static final String NAME_REGEX = "^.{6,20}$";
    private static final String PASSWORD_REGEX = "^\\S{8,32}$";

    public @NotNull UserDataValidationResult forLogin(@NotNull LoginUserData loginUserData) {
        if (loginUserData.getEmailOrName() == null || loginUserData.getPassword() == null) {
            return INVALID_DATA_FORMAT;
        }
        if (!loginUserData.getEmailOrName()
                          .matches(EMAIL_REGEX) || !loginUserData.getEmailOrName()
                                                                 .matches(NAME_REGEX)) {
            return INVALID_EMAIL_OR_NAME;
        }
        if (!loginUserData.getPassword()
                          .matches(PASSWORD_REGEX)) {
            return INVALID_PASSWORD;
        }
        return OK;
    }

    public @NotNull UserDataValidationResult forRegistration(@NotNull UserData userData) {
        if (userData.getEmail() == null || userData.getName() == null || userData.getPassword() == null) {
            return INVALID_DATA_FORMAT;
        }
        if (!userData.getEmail()
                     .matches(EMAIL_REGEX)) {
            return INVALID_EMAIL;
        }
        if (!userData.getName()
                     .matches(NAME_REGEX)) {
            return INVALID_NAME;
        }
        if (!userData.getPassword()
                     .matches(PASSWORD_REGEX)) {
            return INVALID_PASSWORD;
        }
        if (userRepository.findByEmail(userData.getEmail()) != null) {
            return EMAIL_EXISTS;
        }
        if (userRepository.findByName(userData.getName()) != null) {
            return NAME_EXISTS;
        }
        return OK;
    }
}
