package my.page.api.authentication.endpoints;

import my.page.api.authentication.AuthenticationFacade;
import my.page.api.authentication.models.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AuthenticationMapping
public class RegistrationEndpoint {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserData userData) {
        return authenticationFacade.registration(userData);
    }
}
