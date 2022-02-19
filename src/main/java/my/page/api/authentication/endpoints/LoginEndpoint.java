package my.page.api.authentication.endpoints;

import my.page.api.authentication.AuthenticationMapping;
import my.page.api.authentication.enumeration.UserDataValidationResult;
import my.page.api.authentication.services.UserDataValidator;
import my.page.api.authentication.models.LoginUserData;
import my.page.api.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static my.page.api.authentication.enumeration.UserDataValidationResult.OK;
import static my.page.api.authentication.enumeration.UserDataValidationResult.WRONG_CREDENTIALS;

@AuthenticationMapping
public class LoginEndpoint {

    @Autowired
    UserDataValidator userDataValidator;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserData loginUserData) {
        UserDataValidationResult userDataValidationResult = userDataValidator.forLogin(loginUserData);
        if (userDataValidationResult != OK) {
            return ResponseEntity.status(400)
                                 .body(userDataValidationResult.toString());
        }
        if (!userService.isLoginUserData(loginUserData)) {
            return ResponseEntity.status(401)
                                 .body(WRONG_CREDENTIALS.toString());
        }
        String token = "jwt hehe";
        return ResponseEntity.ok(token);
    }
}
