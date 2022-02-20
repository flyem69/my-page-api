package my.page.api.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
public class SecurityConfiguration {

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
        FilterRegistrationBean<AuthorizationFilter> authorizationFilter = new FilterRegistrationBean<>();

        authorizationFilter.setFilter(new AuthorizationFilter());
        authorizationFilter.addUrlPatterns("/streams/*");
        authorizationFilter.setOrder(HIGHEST_PRECEDENCE);

        return authorizationFilter;
    }
}
