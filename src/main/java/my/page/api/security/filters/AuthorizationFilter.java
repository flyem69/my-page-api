package my.page.api.security.filters;

import lombok.AllArgsConstructor;
import my.page.api.security.services.JWTService;
import org.jetbrains.annotations.NotNull;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AuthorizationFilter implements Filter {

    private final JWTService jwtService;

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
            return jwtService.verifyToken(authorizationHeader.substring(7));
        } else {
            return verifyApiKey(authorizationHeader);
        }
    }

    private boolean verifyApiKey(@NotNull String apiKey) {
        return apiKey.equals("xd");
    }
}
