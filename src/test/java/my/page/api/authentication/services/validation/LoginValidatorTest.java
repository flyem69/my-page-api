package my.page.api.authentication.services.validation;

import my.page.api.helpers.UserDataAssembler;
import my.page.api.authentication.enumeration.validation.UserDataValidationResult;
import my.page.api.authentication.models.UserData;
import my.page.api.helpers.UserDataUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginValidatorTest {

    @Autowired
    LoginValidator loginValidator;

    @ParameterizedTest
    @MethodSource("shouldNotValidateUserDataParameters")
    void shouldNotValidateUserData(@NotNull UserData userData, @NotNull UserDataValidationResult validationError) {
        //given
        //when
        UserDataValidationResult result = loginValidator.validate(userData);

        //then
        assertEquals(validationError, result);
    }

    @Test
    void shouldValidateUserData() {
        //given
        UserData userData = UserDataAssembler.make()
                                             .withAnyEmail()
                                             .withAnyPassword()
                                             .assemble();

        //when
        UserDataValidationResult result = loginValidator.validate(userData);

        //then
        assertEquals(UserDataValidationResult.OK, result);
    }

    private static @NotNull Stream<Arguments> shouldNotValidateUserDataParameters() {
        return Stream.of(userDataWithInvalidDataFormat(), UserDataUtils.userDataWithInvalidEmail())
                     .flatMap(stream -> stream);
    }

    private static @NotNull Stream<Arguments> userDataWithInvalidDataFormat() {
        return Stream.of(
                Arguments.of(UserDataAssembler.make()
                                              .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT),
                Arguments.of(UserDataAssembler.make()
                                              .withAnyEmail()
                                              .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT),
                Arguments.of(UserDataAssembler.make()
                                              .withAnyPassword()
                                              .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT)
        );
    }
}