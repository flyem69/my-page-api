package my.page.api.authentication.endpoints;

import my.page.api.authentication.AuthenticationMapping;
import my.page.api.authentication.enumeration.UserDataValidationResult;
import my.page.api.authentication.models.UserData;
import my.page.api.authentication.services.UserDataValidator;
import my.page.api.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static my.page.api.authentication.enumeration.UserDataValidationResult.OK;

@AuthenticationMapping
public class Registration {

    @Autowired
    UserDataValidator userDataValidator;

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserData userData) {
        UserDataValidationResult userDataValidationResult = userDataValidator.forRegistration(userData);
        if (userDataValidationResult != OK) {
            return ResponseEntity.status(400)
                                 .body(userDataValidationResult.toString());
        }
        userService.createNewUser(userData);
        String token = "jwt hehe";
        return ResponseEntity.ok(token);
    }
}
