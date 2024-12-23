package com.web.time_to_book.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import org.springframework.security.web.context.SecurityContextRepository;

import com.web.time_to_book.models.enums.UserRoles;
import com.web.time_to_book.repositories.UserRepository;
import com.web.time_to_book.services.impl.AppUserDetailsService;

@Configuration
public class SecurityConfiguration {

    private UserRepository userRepository;

    @Autowired
    public SecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            SecurityContextRepository securityContextRepository) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers("/static/**", "/css/**", "/js/**",
                                        "/img/**")
                                .permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/", "/login", "/registration",
                                        "/login/error")
                                .permitAll().requestMatchers("/{id}").authenticated()
                                .requestMatchers("/all/master",
                                        "/employees/employee-delete/")
                                .hasAnyRole(UserRoles.MODERATOR.name(),
                                        UserRoles.ADMIN.name())
                                .requestMatchers("/companies/company-delete/")
                                .hasRole(UserRoles.ADMIN.name())
                                .anyRequest().authenticated())
                .formLogin(
                        (formLogin) -> formLogin.loginPage("/login")
                                .usernameParameter(
                                        UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                .passwordParameter(
                                        UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                                .defaultSuccessUrl("/")
                                .failureForwardUrl("/login/error"))
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
                .securityContext(
                        securityContext -> securityContext
                                .securityContextRepository(securityContextRepository))
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember")
                        .key("remember Me Encryption Key")
                        .rememberMeCookieName("rememberMeCookieName")
                        .tokenValiditySeconds(3600));

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(userRepository);
    }
}
