package my.page.api.security;

import my.page.api.security.filters.AuthorizationFilter;
import my.page.api.security.services.JWTService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
public class SecurityConfiguration {

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter(JWTService jwtService) {
        FilterRegistrationBean<AuthorizationFilter> authorizationFilter = new FilterRegistrationBean<>();

        authorizationFilter.setFilter(new AuthorizationFilter(jwtService));
        authorizationFilter.addUrlPatterns("/streams/*");
        authorizationFilter.setOrder(HIGHEST_PRECEDENCE);

        return authorizationFilter;
    }
}
