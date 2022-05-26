package my.page.api.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import my.page.api.authentication.models.UserData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static org.apache.commons.lang3.RandomStringUtils.random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserDataAssembler {

    private String email;
    private String name;
    private String password;

    @Contract(" -> new")
    public static @NotNull UserDataAssembler make() {
        return new UserDataAssembler();
    }

    public static @NotNull UserDataAssembler make(@NotNull UserDataAssembler userDataAssembler) {
        return new UserDataAssembler().withEmail(userDataAssembler.email)
                                      .withName(userDataAssembler.name)
                                      .withPassword(userDataAssembler.password);
    }

    public @NotNull UserDataAssembler withEmail(String email) {
        this.email = email;
        return this;
    }

    public @NotNull UserDataAssembler withAnyEmail() {
        this.email = random(5, true, false)
                     + "@" +
                     random(4, true, false)
                     + "." +
                     random(2, true, false);
        return this;
    }

    public @NotNull UserDataAssembler withName(String name) {
        this.name = name;
        return this;
    }

    public @NotNull UserDataAssembler withAnyName() {
        this.name = random(10, true, true);
        return this;
    }

    public @NotNull UserDataAssembler withPassword(String password) {
        this.password = password;
        return this;
    }

    public @NotNull UserDataAssembler withAnyPassword() {
        this.password = random(12, true, true);
        return this;
    }

    public @NotNull UserData assemble() {
        UserData userData = new UserData();
        userData.setEmail(email);
        userData.setName(name);
        userData.setPassword(password);
        return userData;
    }
}
