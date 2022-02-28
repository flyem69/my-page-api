package my.page.api.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.io.Resources;
import my.page.api.authentication.dao.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class JWTService {

    @Autowired
    UserRepository userRepository;

    private static final JWTVerifier jwtVerifier;
    private static final Algorithm jwtAlgorithm;

    static {
        jwtAlgorithm = Algorithm.HMAC256(getSecret());
        jwtVerifier = JWT.require(jwtAlgorithm)
                         .withIssuer("my-page")
                         .build();
    }

    public String generateToken(String userId) {
        return JWT.create()
                  .withIssuer("my-page")
                  .withSubject(userId)
                  .sign(jwtAlgorithm);
    }

    public boolean verifyToken(@NotNull String token) {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            String subject = decodedJWT.getSubject();
            if (subject != null) {
                return userRepository.findById(UUID.fromString(subject))
                                     .isPresent();
            }
            return false;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            return false;
        }
    }

    private static String getSecret() {
        URL url = Resources.getResource("jwt/secret.txt");
        try {
            return Resources.toString(url, UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
