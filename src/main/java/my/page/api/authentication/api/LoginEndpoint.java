package my.page.api.authentication.api;

import my.page.api.authentication.AuthenticationFacade;
import my.page.api.authentication.models.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AuthenticationMapping
public class LoginEndpoint {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserData userData) {
        return authenticationFacade.login(userData);
    }
}
