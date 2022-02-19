package my.page.api.authentication.services;

import my.page.api.assemblers.LoginUserDataAssembler;
import my.page.api.assemblers.UserDataAssembler;
import my.page.api.authentication.enumeration.UserDataValidationResult;
import my.page.api.authentication.models.LoginUserData;
import my.page.api.authentication.models.UserData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static my.page.api.authentication.enumeration.UserDataValidationResult.*;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserDataValidatorTest {

    @Autowired
    UserDataValidator userDataValidator;

    @ParameterizedTest
    @MethodSource("loginUserDataWithInvalidDataFormatProvider")
    void loginUserDataWithInvalidDataFormat(LoginUserData loginUserDataWithInvalidDataFormat) {
        assertEquals(INVALID_DATA_FORMAT, userDataValidator.forLogin(loginUserDataWithInvalidDataFormat));
    }

    @ParameterizedTest
    @MethodSource("userDataWithInvalidDataFormatProvider")
    void userDataWithInvalidDataFormat(UserData userDataWithInvalidDataFormat) {
        assertEquals(INVALID_DATA_FORMAT, userDataValidator.forRegistration(userDataWithInvalidDataFormat));
    }

    @ParameterizedTest
    @MethodSource("userDataWithInvalidEmailProvider")
    void userDataWithInvalidEmail(UserData userDataWithInvalidEmail) {
        assertEquals(INVALID_EMAIL, userDataValidator.forRegistration(userDataWithInvalidEmail));
    }

    @ParameterizedTest
    @MethodSource("userDataWithInvalidNameProvider")
    void userDataWithInvalidName(UserData userDataWithInvalidName) {
        assertEquals(INVALID_NAME, userDataValidator.forRegistration(userDataWithInvalidName));
    }

    @ParameterizedTest
    @MethodSource("userDataWithInvalidPasswordProvider")
    void userDataWithInvalidPassword(LoginUserData loginUserDataWithInvalidPassword,
                                     UserData userDataWithInvalidPassword) {
        assertAll(
                () -> assertEquals(INVALID_PASSWORD, userDataValidator.forLogin(loginUserDataWithInvalidPassword)),
                () -> assertEquals(INVALID_PASSWORD, userDataValidator.forRegistration(userDataWithInvalidPassword))
        );
    }

    @Test
    void validUserData() {
        //given
        LoginUserData validLoginUserDataForLogin = LoginUserDataAssembler.make()
                                                                         .withAnyEmailOrName()
                                                                         .withAnyPassword()
                                                                         .assemble();
        UserData validUserDataForRegistration = UserDataAssembler.make()
                                                                 .withAnyEmail()
                                                                 .withAnyName()
                                                                 .withAnyPassword()
                                                                 .assemble();

        //when
        UserDataValidationResult loginResult = userDataValidator.forLogin(validLoginUserDataForLogin);
        UserDataValidationResult registrationResult = userDataValidator.forRegistration(validUserDataForRegistration);

        //then
        assertAll(
                () -> assertEquals(OK, loginResult),
                () -> assertEquals(OK, registrationResult)
        );
    }

    private static @NotNull Stream<LoginUserData> loginUserDataWithInvalidDataFormatProvider() {
        return Stream.of(
                LoginUserDataAssembler.make()
                                      .assemble(),
                LoginUserDataAssembler.make()
                                      .withAnyEmailOrName()
                                      .assemble(),
                LoginUserDataAssembler.make()
                                      .withAnyPassword()
                                      .assemble()
        );
    }

    private static @NotNull Stream<UserData> userDataWithInvalidDataFormatProvider() {
        return Stream.of(
                UserDataAssembler.make()
                                 .assemble(),
                UserDataAssembler.make()
                                 .withAnyEmail()
                                 .assemble(),
                UserDataAssembler.make()
                                 .withAnyName()
                                 .assemble(),
                UserDataAssembler.make()
                                 .withAnyPassword()
                                 .assemble(),
                UserDataAssembler.make()
                                 .withAnyEmail()
                                 .withAnyPassword()
                                 .assemble(),
                UserDataAssembler.make()
                                 .withAnyName()
                                 .withAnyPassword()
                                 .assemble(),
                UserDataAssembler.make()
                                 .withAnyEmail()
                                 .withAnyName()
                                 .assemble()
        );
    }

    private static @NotNull Stream<UserData> userDataWithInvalidEmailProvider() {
        UserDataAssembler validPrefix = UserDataAssembler.make()
                                                         .withAnyName()
                                                         .withAnyPassword();
        String tooLongEmail = random(65, true, true)
                              + "@" +
                              random(65, true, true)
                              + "." +
                              random(254, true, true)
                              + "." +
                              random(64, true, true);
        return Stream.of(
                UserDataAssembler.make(validPrefix)
                                 .withEmail("")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withEmail("123")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withEmail("abc@")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withEmail("@wp")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withEmail("@o2.pl")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withEmail("@123")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withEmail("user@o2")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withEmail("user@.pl")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withEmail(tooLongEmail)
                                 .assemble()
        );
    }

    private static @NotNull Stream<UserData> userDataWithInvalidNameProvider() {
        UserDataAssembler validPrefix = UserDataAssembler.make()
                                                         .withAnyEmail()
                                                         .withAnyPassword();
        String tooLongName = random(21, true, true);
        return Stream.of(
                UserDataAssembler.make(validPrefix)
                                 .withName("")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withName("123")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withName("abc")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withName("1a!@#")
                                 .assemble(),
                UserDataAssembler.make(validPrefix)
                                 .withName(tooLongName)
                                 .assemble()
        );
    }

    private static @NotNull Stream<Arguments> userDataWithInvalidPasswordProvider() {
        LoginUserDataAssembler validPrefixForLogin = LoginUserDataAssembler.make()
                                                                           .withAnyEmailOrName();
        UserDataAssembler validPrefixForRegistration = UserDataAssembler.make()
                                                                        .withAnyEmail()
                                                                        .withAnyName();
        String[] invalidPasswords = {
                "", "1234567", "abcdefg", "!@#$%^&", random(33, true, true)
        };
        return Stream.of(
                Arguments.of(LoginUserDataAssembler.make(validPrefixForLogin)
                                                   .withPassword(invalidPasswords[0])
                                                   .assemble(), UserDataAssembler.make(validPrefixForRegistration)
                                                                                 .withPassword(invalidPasswords[0])
                                                                                 .assemble()),
                Arguments.of(LoginUserDataAssembler.make(validPrefixForLogin)
                                                   .withPassword(invalidPasswords[1])
                                                   .assemble(), UserDataAssembler.make(validPrefixForRegistration)
                                                                                 .withPassword(invalidPasswords[1])
                                                                                 .assemble()),
                Arguments.of(LoginUserDataAssembler.make(validPrefixForLogin)
                                                   .withPassword(invalidPasswords[2])
                                                   .assemble(), UserDataAssembler.make(validPrefixForRegistration)
                                                                                 .withPassword(invalidPasswords[2])
                                                                                 .assemble()),
                Arguments.of(LoginUserDataAssembler.make(validPrefixForLogin)
                                                   .withPassword(invalidPasswords[3])
                                                   .assemble(), UserDataAssembler.make(validPrefixForRegistration)
                                                                                 .withPassword(invalidPasswords[3])
                                                                                 .assemble()),
                Arguments.of(LoginUserDataAssembler.make(validPrefixForLogin)
                                                   .withPassword(invalidPasswords[4])
                                                   .assemble(), UserDataAssembler.make(validPrefixForRegistration)
                                                                                 .withPassword(invalidPasswords[4])
                                                                                 .assemble())
        );
    }
}
