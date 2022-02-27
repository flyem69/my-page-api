package my.page.api.authentication.endpoints;

import my.page.api.authentication.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AuthenticationMapping
public class VerificationEndpoint {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("/verification")
    public ResponseEntity<String> verification(@RequestBody String token) {
        return authenticationFacade.verification(token);
    }
}
