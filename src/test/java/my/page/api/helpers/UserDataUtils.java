package my.page.api.helpers;

import my.page.api.authentication.enumeration.validation.UserDataValidationResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public abstract class UserDataUtils {

    public static @NotNull Stream<Arguments> userDataWithInvalidEmail() {
        UserDataAssembler userDataAssemblerWithValidNameAndPassword = UserDataAssembler.make()
                                                                                       .withAnyName()
                                                                                       .withAnyPassword();
        String tooLongEmail = RandomStringUtils.random(65, true, true)
                              + "@" +
                              RandomStringUtils.random(65, true, true)
                              + "." +
                              RandomStringUtils.random(254, true, true)
                              + "." +
                              RandomStringUtils.random(64, true, true);
        return Stream.of(
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail("")
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail("123")
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail("abc@")
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail("@wp")
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail("@o2.pl")
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail("@123")
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail("user@o2")
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail("user@.pl")
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidNameAndPassword)
                                              .withEmail(tooLongEmail)
                                              .assemble(), UserDataValidationResult.INVALID_EMAIL)
        );
    }

    public static @NotNull Stream<Arguments> userDataWithInvalidName() {
        UserDataAssembler userDataAssemblerWithValidEmailAndPassword = UserDataAssembler.make()
                                                                                        .withAnyEmail()
                                                                                        .withAnyPassword();
        String tooLongName = RandomStringUtils.random(21, true, true);
        return Stream.of(
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndPassword)
                                              .withName("")
                                              .assemble(), UserDataValidationResult.INVALID_NAME),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndPassword)
                                              .withName("123")
                                              .assemble(), UserDataValidationResult.INVALID_NAME),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndPassword)
                                              .withName("abc")
                                              .assemble(), UserDataValidationResult.INVALID_NAME),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndPassword)
                                              .withName("1a!@#")
                                              .assemble(), UserDataValidationResult.INVALID_NAME),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndPassword)
                                              .withName(tooLongName)
                                              .assemble(), UserDataValidationResult.INVALID_NAME)
        );
    }

    public static @NotNull Stream<Arguments> userDataWithInvalidPassword() {
        UserDataAssembler userDataAssemblerWithValidEmailAndName = UserDataAssembler.make()
                                                                                    .withAnyEmail()
                                                                                    .withAnyName();
        String tooLongPassword = RandomStringUtils.random(33, true, true);
        return Stream.of(
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndName)
                                              .withPassword("")
                                              .assemble(), UserDataValidationResult.INVALID_PASSWORD),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndName)
                                              .withPassword("1234567")
                                              .assemble(), UserDataValidationResult.INVALID_PASSWORD),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndName)
                                              .withPassword("abcdefg")
                                              .assemble(), UserDataValidationResult.INVALID_PASSWORD),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndName)
                                              .withPassword("!@#$%^&")
                                              .assemble(), UserDataValidationResult.INVALID_PASSWORD),
                Arguments.of(UserDataAssembler.make(userDataAssemblerWithValidEmailAndName)
                                              .withPassword(tooLongPassword)
                                              .assemble(), UserDataValidationResult.INVALID_PASSWORD)
        );
    }
}
