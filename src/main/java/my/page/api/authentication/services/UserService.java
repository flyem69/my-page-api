package my.page.api.authentication.services;

import my.page.api.authentication.dao.UserRepository;
import my.page.api.authentication.models.User;
import my.page.api.authentication.models.UserData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public @Nullable User findUserByUserData(@NotNull UserData userData) {
        User user = userRepository.findByEmailOrName(userData.getEmail(), userData.getName());
        if (user != null && isPasswordMatch(userData.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }

    public @NotNull User createNewUser(@NotNull UserData userData) {
        //TODO throw custom exception if invalid data
        User user = new User();
        user.setEmail(userData.getEmail());
        user.setName(userData.getName());
        user.setPassword(userData.getPassword());
        return userRepository.save(user);
    }

    private boolean isPasswordMatch(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
