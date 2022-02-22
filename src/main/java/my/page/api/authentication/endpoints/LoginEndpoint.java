package my.page.api.authentication.endpoints;

import my.page.api.authentication.AuthenticationFacade;
import my.page.api.authentication.models.LoginUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AuthenticationMapping
public class LoginEndpoint {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserData loginUserData) {
        return authenticationFacade.login(loginUserData);
    }
}
