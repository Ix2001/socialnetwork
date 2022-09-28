package com.socialnetwork.org.springsecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthFilter authFilter = new AuthFilter(authenticationManager());
        authFilter.setFilterProcessesUrl("/login");
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/**").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/db").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable()
                .addFilter(authFilter)
                .addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    protected PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration().getAuthenticationManager();
    }
    @Bean
    AuthenticationConfiguration authenticationConfiguration() {
        AuthenticationConfiguration authenticationConfiguration;
        authenticationConfiguration = new AuthenticationConfiguration();
        return authenticationConfiguration;
    }
}
