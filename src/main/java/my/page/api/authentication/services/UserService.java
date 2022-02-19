package my.page.api.authentication.services;

import my.page.api.authentication.dao.UserRepository;
import my.page.api.authentication.models.LoginUserData;
import my.page.api.authentication.models.User;
import my.page.api.authentication.models.UserData;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean isLoginUserData(@NotNull LoginUserData loginUserData) {
        List<User> users = userRepository.findByEmailOrName(loginUserData.getEmailOrName(),
                                                            loginUserData.getEmailOrName());
        for (User user : users) {
            if (isPasswordMatch(loginUserData.getPassword(), user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public void createNewUser(@NotNull UserData userData) {
        User user = new User();
        user.setEmail(userData.getEmail());
        user.setName(userData.getName());
        user.setPassword(userData.getPassword());
        userRepository.save(user);
    }

    private boolean isPasswordMatch(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
