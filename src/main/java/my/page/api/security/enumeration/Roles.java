package my.page.api.security.enumeration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Roles {

    public static final String USER = "USER";

    public static final String SYSTEM = "SYSTEM";
}
