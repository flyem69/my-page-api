package my.page.api.authentication.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

import java.util.UUID;

import static my.page.api.database.Schemas.AUTHENTICATION;

@Entity
@Table(name = "users", schema = AUTHENTICATION)
@Getter
public class User {

    @Transient
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue
    @Setter
    private UUID id;
    @Setter
    @Column(nullable = false, unique = true)
    private String email;
    @Setter
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;

    public void setPassword(String password) {
        this.password = encoder.encode(password);
    }
}
