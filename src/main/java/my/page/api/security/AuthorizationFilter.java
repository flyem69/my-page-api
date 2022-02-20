package my.page.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import org.jetbrains.annotations.NotNull;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    private static final JWTVerifier jwtVerifier;

    static {
        Algorithm jwtAlgorithm = Algorithm.HMAC256("qwerty");
        jwtVerifier = JWT.require(jwtAlgorithm)
                         .withIssuer("my-page")
                         .build();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorizationHeader = httpRequest.getHeader("Authorization");
        if (authorizationHeader != null && verifyHeader(authorizationHeader)) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }
        httpResponse.reset();
        httpResponse.setStatus(401);
    }

    @Override
    public void destroy() {
        //do nothing
    }

    private boolean verifyHeader(@NotNull String authorizationHeader) {
        if (authorizationHeader.startsWith("Bearer ")) {
            return verifyToken(authorizationHeader.substring(7));
        } else {
            return verifyApiKey(authorizationHeader);
        }
    }

    private boolean verifyToken(@NotNull String token) {
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException exception) {
            return false;
        }
        return true;
    }

    private boolean verifyApiKey(@NotNull String apiKey) {
        return apiKey.equals("xd");
    }
}
