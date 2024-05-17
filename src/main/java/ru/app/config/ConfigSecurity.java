package ru.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;


    // Доступы
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/css/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product-create", "/product-delete/").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/product-create").hasAnyRole("ADMIN")
                        .requestMatchers("/products", "/buy-product/", "/download/", "/sell-product/").hasAnyRole("ADMIN", "USER")


                        .requestMatchers("/public-date").hasAnyRole("USERS", "ADMIN")
                        .anyRequest()
                        .permitAll()


                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler)
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/logout")
                        .permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/login"));
        return httpSecurity.build();
    }

    // шифрование пароля
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    UserDetailsManager userDetailsManager() {
        var user1 = User.withUsername("user").password("{noop}password").roles("USER").build();
        var user2 = User.withUsername("admin").password("{noop}password").roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
