package ph.agh.tiwo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import ph.agh.tiwo.security.filters.EmailAndPasswordAuthenticationFilter;
import ph.agh.tiwo.security.filters.JwtTokenVerifierFilter;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class GlobalSecurityConfiguration {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenGenerator tokenGenerator;
    private final JwtTokenVerifier tokenVerifier;

    @Bean
    public SecurityFilterChain filterEndpoints(HttpSecurity security) throws Exception {
        security.sessionManagement().sessionCreationPolicy(STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and().csrf().disable()
                .cors().configurationSource((request) -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080", "http://localhost:8090"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(Collections.singletonList("Authorization"));
                    config.setMaxAge(3600L);
                    return config;
                }).and()
                .addFilter(getEmailAndPasswordAuthenticationFilter())
                .addFilterAfter(new JwtTokenVerifierFilter(tokenVerifier), EmailAndPasswordAuthenticationFilter.class).
                authorizeHttpRequests().antMatchers("/tiwo/user/login", "/login").permitAll().and().
                authorizeHttpRequests().antMatchers("/tiwo/user/register", "/register").permitAll()
                .anyRequest().authenticated();
        return security.build();
    }

    @Bean
    public PasswordEncoder bcrypt() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public EmailAndPasswordAuthenticationFilter getEmailAndPasswordAuthenticationFilter() throws Exception {
        final EmailAndPasswordAuthenticationFilter filter = new EmailAndPasswordAuthenticationFilter(
                authenticationManager(authenticationConfiguration),
                this.tokenGenerator);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        filter.setFilterProcessesUrl("/tiwo/user/login");
        return filter;
    }
}
