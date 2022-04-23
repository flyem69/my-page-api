package my.page.api.authentication.models;

import lombok.Getter;
import lombok.Setter;
import my.page.api.database.Schemas;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users", schema = Schemas.AUTHENTICATION)
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
