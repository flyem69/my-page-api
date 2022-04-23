package my.page.api.authentication.enumeration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationRegex {

    public static final String EMAIL = "^\\S{1,64}@(\\S{1,64}\\.)*\\S{1,253}\\.\\S{1,63}$";
    public static final String NAME = "^.{6,20}$";
    public static final String PASSWORD = "^\\S{8,32}$";
}
