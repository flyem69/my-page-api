package my.page.api.assemblers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import my.page.api.authentication.models.LoginUserData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static org.apache.commons.lang3.RandomStringUtils.random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUserDataAssembler {

    private String emailOrName;
    private String password;

    @Contract(" -> new")
    public static @NotNull LoginUserDataAssembler make() {
        return new LoginUserDataAssembler();
    }

    public static @NotNull LoginUserDataAssembler make(@NotNull LoginUserDataAssembler loginUserDataAssembler) {
        return new LoginUserDataAssembler().withEmailOrName(loginUserDataAssembler.emailOrName)
                                           .withPassword(loginUserDataAssembler.password);
    }

    public @NotNull LoginUserDataAssembler withEmailOrName(String emailOrName) {
        this.emailOrName = emailOrName;
        return this;
    }

    public @NotNull LoginUserDataAssembler withAnyEmailOrName() {
        this.emailOrName = random(5, true, false)
                           + "@" +
                           random(4, true, false)
                           + "." +
                           random(2, true, false);
        return this;
    }

    public @NotNull LoginUserDataAssembler withPassword(String password) {
        this.password = password;
        return this;
    }

    public @NotNull LoginUserDataAssembler withAnyPassword() {
        this.password = random(12, true, true);
        return this;
    }

    public @NotNull LoginUserData assemble() {
        LoginUserData loginUserData = new LoginUserData();
        loginUserData.setEmailOrName(emailOrName);
        loginUserData.setPassword(password);
        return loginUserData;
    }
}
