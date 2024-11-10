//package com.example.CRUDdemo.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.sql.DataSource;
//
//@Configuration
//
//public class demoSecurityConfig {
//
//    // add support for JDBC
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource){
//
//        return new JdbcUserDetailsManager(dataSource);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(configurer -> configurer
//                .requestMatchers(HttpMethod.GET,
//                        "/api/members").hasRole("EMPLOYEE")
//                .requestMatchers(HttpMethod.GET, "/api/members/**").hasRole("EMPLOYEE")
//                .requestMatchers(HttpMethod.POST, "/api/members").hasRole("MANAGER")
//                .requestMatchers(HttpMethod.PUT, "/api/members/**").hasRole("MANAGER")
//                .requestMatchers(HttpMethod.DELETE, "/api/members/**").hasRole("ADMIN"));
//
//
//        // use HTTP Basic authentication
//        http.httpBasic(Customizer.withDefaults());
//
//        // disable Cross Site Request Forgery (CSRF)
//        http.csrf(csrf -> csrf.disable());
//
//        return http.build();
//
//    }
//}
