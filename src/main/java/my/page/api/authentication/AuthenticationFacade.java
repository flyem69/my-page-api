package my.page.api.authentication;

import lombok.AllArgsConstructor;
import my.page.api.authentication.enumeration.validation.UserDataValidationResult;
import my.page.api.authentication.models.User;
import my.page.api.authentication.models.UserData;
import my.page.api.authentication.services.UserService;
import my.page.api.authentication.services.validation.LoginValidator;
import my.page.api.authentication.services.validation.RegistrationValidator;
import my.page.api.security.services.JWTService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static my.page.api.authentication.enumeration.validation.UserDataValidationResult.OK;
import static my.page.api.authentication.enumeration.validation.UserDataValidationResult.WRONG_CREDENTIALS;

@Component
@AllArgsConstructor
public class AuthenticationFacade {

    private final LoginValidator loginValidator;
    private final RegistrationValidator registrationValidator;
    private final UserService userService;
    private final JWTService jwtService;

    public @NotNull ResponseEntity<String> login(@NotNull UserData userData) {
        UserDataValidationResult userDataValidationResult = loginValidator.validate(userData);
        if (userDataValidationResult != OK) {
            return ResponseEntity.status(400)
                                 .body(userDataValidationResult.toString());
        }
        User user = userService.findUserByUserData(userData);
        if (user == null) {
            return ResponseEntity.status(400)
                                 .body(WRONG_CREDENTIALS.toString());
        }
        String token = jwtService.generateToken(user.getId()
                                                    .toString());
        return ResponseEntity.ok(token);
    }

    public @NotNull ResponseEntity<String> registration(@NotNull UserData userData) {
        UserDataValidationResult userDataValidationResult = registrationValidator.validate(userData);
        if (userDataValidationResult != OK) {
            return ResponseEntity.status(400)
                                 .body(userDataValidationResult.toString());
        }
        User user = userService.createNewUser(userData);
        String token = jwtService.generateToken(user.getId()
                                                    .toString());
        return ResponseEntity.ok(token);
    }

    public @NotNull ResponseEntity<String> verification(@NotNull String token) {
        if (jwtService.verifyToken(token)) {
            return ResponseEntity.ok()
                                 .build();
        } else {
            return ResponseEntity.status(401)
                                 .build();
        }
    }
}
