package my.page.api.authentication.services.validation;

import static org.mockito.ArgumentMatchers.anyString;

import my.page.api.assemblers.UserDataAssembler;
import my.page.api.authentication.dao.UserRepository;
import my.page.api.authentication.enumeration.validation.UserDataValidationResult;
import my.page.api.authentication.models.User;
import my.page.api.authentication.models.UserData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationValidatorTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    RegistrationValidator registrationValidator;

    @ParameterizedTest
    @MethodSource("shouldNotValidateUserDataParameters")
    void shouldNotValidateUserData(@NotNull UserData userData, @NotNull UserDataValidationResult validationError) {
        //given
        Mockito.lenient().when(userRepository.findByEmail(anyString())).thenReturn(null);
        Mockito.lenient().when(userRepository.findByName(anyString())).thenReturn(null);

        //when
        UserDataValidationResult result = registrationValidator.validate(userData);

        //then
        assertEquals(validationError, result);
    }

    @Test
    void shouldNotValidateUserDataWhenEmailExists() {
        //given
        Mockito.lenient().when(userRepository.findByEmail(anyString())).thenReturn(new User());
        Mockito.lenient().when(userRepository.findByName(anyString())).thenReturn(null);
        UserData userData = UserDataAssembler.make()
                                             .withAnyEmail()
                                             .withAnyName()
                                             .withAnyPassword()
                                             .assemble();

        //when
        UserDataValidationResult result = registrationValidator.validate(userData);

        //then
        assertEquals(UserDataValidationResult.EMAIL_EXISTS, result);
    }

    @Test
    void shouldNotValidateUserDataWhenNameExists() {
        //given
        Mockito.lenient().when(userRepository.findByEmail(anyString())).thenReturn(null);
        Mockito.lenient().when(userRepository.findByName(anyString())).thenReturn(new User());
        UserData userData = UserDataAssembler.make()
                                             .withAnyEmail()
                                             .withAnyName()
                                             .withAnyPassword()
                                             .assemble();

        //when
        UserDataValidationResult result = registrationValidator.validate(userData);

        //then
        assertEquals(UserDataValidationResult.NAME_EXISTS, result);
    }

    @Test
    void shouldValidateUserData() {
        //given
        Mockito.lenient().when(userRepository.findByEmail(anyString())).thenReturn(null);
        Mockito.lenient().when(userRepository.findByName(anyString())).thenReturn(null);
        UserData userData = UserDataAssembler.make()
                                             .withAnyEmail()
                                             .withAnyName()
                                             .withAnyPassword()
                                             .assemble();

        //when
        UserDataValidationResult result = registrationValidator.validate(userData);

        //then
        assertEquals(UserDataValidationResult.OK, result);
    }

    private static @NotNull Stream<Arguments> shouldNotValidateUserDataParameters() {
        return Stream.of(userDataWithInvalidDataFormat(),
                         UserDataUtils.userDataWithInvalidEmail(),
                         UserDataUtils.userDataWithInvalidName(),
                         UserDataUtils.userDataWithInvalidPassword())
                     .flatMap(stream -> stream);
    }

    private static @NotNull Stream<Arguments> userDataWithInvalidDataFormat() {
        return Stream.of(Arguments.of(UserDataAssembler.make()
                                                       .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT),
                         Arguments.of(UserDataAssembler.make()
                                                       .withAnyName()
                                                       .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT),
                         Arguments.of(UserDataAssembler.make()
                                                       .withAnyEmail()
                                                       .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT),
                         Arguments.of(UserDataAssembler.make()
                                                       .withAnyPassword()
                                                       .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT),
                         Arguments.of(UserDataAssembler.make()
                                                       .withAnyEmail()
                                                       .withAnyName()
                                                       .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT),
                         Arguments.of(UserDataAssembler.make()
                                                       .withAnyEmail()
                                                       .withAnyPassword()
                                                       .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT),
                         Arguments.of(UserDataAssembler.make()
                                                       .withAnyName()
                                                       .withAnyPassword()
                                                       .assemble(), UserDataValidationResult.INVALID_DATA_FORMAT));
    }
}