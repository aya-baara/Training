package com.example.CRUDdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    // to handle my own login page
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers( "/").hasRole("EMPLOYEE")
                                .requestMatchers( "leaders/**").hasRole("MANAGER")
                                .requestMatchers( "systems/**").hasRole("ADMIN")

                .anyRequest().authenticated()
    )
            .formLogin(form ->
                form
                        .loginPage("/showMyLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")

                .permitAll()

            ).logout((logout->logout.permitAll())
    )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
);

        return http.build();
    }
}