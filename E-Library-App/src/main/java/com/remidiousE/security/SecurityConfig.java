//package com.remidiousE.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.SecurityBuilder;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class  SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
//        http
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers(antMatcher(HttpMethod.GET, "/admin/**")).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//
//        return http.build();
//
//    }
//
//    @Bean
//    public UserDetailsService users(){
//        UserDetails admins = User.builder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admins, user);
//    }
//
//}
