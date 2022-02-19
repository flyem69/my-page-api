package my.page.api.authentication.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserData {

    private String emailOrName;
    private String password;
}
