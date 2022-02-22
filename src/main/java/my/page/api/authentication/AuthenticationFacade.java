package my.page.api.authentication;

import my.page.api.authentication.enumeration.UserDataValidationResult;
import my.page.api.authentication.models.LoginUserData;
import my.page.api.authentication.models.User;
import my.page.api.authentication.models.UserData;
import my.page.api.authentication.services.UserDataValidator;
import my.page.api.authentication.services.UserService;
import my.page.api.security.services.JWTService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static my.page.api.authentication.enumeration.UserDataValidationResult.OK;
import static my.page.api.authentication.enumeration.UserDataValidationResult.WRONG_CREDENTIALS;

@Component
public class AuthenticationFacade {

    @Autowired
    UserDataValidator userDataValidator;

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    public @NotNull ResponseEntity<String> login(@NotNull LoginUserData loginUserData) {
        UserDataValidationResult userDataValidationResult = userDataValidator.forLogin(loginUserData);
        if (userDataValidationResult != OK) {
            return ResponseEntity.status(400)
                                 .body(userDataValidationResult.toString());
        }
        User user = userService.findUserByLoginUserData(loginUserData);
        if (user == null) {
            return ResponseEntity.status(401)
                                 .body(WRONG_CREDENTIALS.toString());
        }
        String token = jwtService.generateToken(user.getId()
                                                    .toString());
        return ResponseEntity.ok(token);
    }

    public @NotNull ResponseEntity<String> registration(@NotNull UserData userData) {
        UserDataValidationResult userDataValidationResult = userDataValidator.forRegistration(userData);
        if (userDataValidationResult != OK) {
            return ResponseEntity.status(400)
                                 .body(userDataValidationResult.toString());
        }
        User user = userService.createNewUser(userData);
        String token = jwtService.generateToken(user.getId()
                                                    .toString());
        return ResponseEntity.ok(token);
    }
}
