package com.amany.onlinecourses_demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(HttpMethod.POST,"/students/processForm").permitAll()
                                .requestMatchers(HttpMethod.POST,"/instructors/processForm").permitAll()
                                .requestMatchers(HttpMethod.GET,"/students/registration").permitAll()
                                .requestMatchers(HttpMethod.GET, "/courses/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/enroll/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/instructors/registration").permitAll()
                                .requestMatchers(HttpMethod.GET,"/admin/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/admin/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/dashboard").hasAnyRole("ADMIN", "STUDENT", "INSTRUCTOR")
                                .requestMatchers(HttpMethod.GET,"/students/**").hasAnyRole("ADMIN", "STUDENT")
                                .requestMatchers(HttpMethod.POST,"/students/**").hasAnyRole("ADMIN", "STUDENT")
                                .requestMatchers(HttpMethod.PUT,"/students/**").hasAnyRole("ADMIN", "STUDENT")
                                .requestMatchers(HttpMethod.DELETE,"/students/**").hasAnyRole("ADMIN", "STUDENT")
                                .requestMatchers(HttpMethod.DELETE,"/instructors/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                                .requestMatchers(HttpMethod.GET,"/instructors/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                                .requestMatchers(HttpMethod.POST,"/instructors/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                                .requestMatchers(HttpMethod.PUT,"/instructors/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                                .requestMatchers("/access-denied").permitAll()
                                .requestMatchers("/").permitAll())
                .exceptionHandling(configurer ->
                        configurer
                                .accessDeniedPage("/access-denied"))
                .logout(logout ->logout.permitAll())
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll());

        return http.build( );
    }

    @Bean
    public UserDetailsManager userDetailsManager (DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT user_name, pw, active FROM members WHERE user_name=?"
        );
        // define query to retrieve authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT user_id, role from roles where user_id=?"
        );
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    
}