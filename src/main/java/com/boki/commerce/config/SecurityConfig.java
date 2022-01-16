package com.boki.commerce.config;

import static org.springframework.http.HttpMethod.OPTIONS;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    String[] pathArray = new String[]{
        "/h2-console/**", "/favicon.ico", "/error", "/webjars/**", "/configuration/**",
        "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger/**", "/swagger-ui/**"
        , "/swagger-ui/index.html", "/swagger-ui.html"};

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(pathArray);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .formLogin().disable()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(OPTIONS).permitAll() // CORS
            .antMatchers("/api/users/me", "/api/users/logout", "/api/orders/**").authenticated()
            .anyRequest().permitAll()

            .and()
            .cors()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(1);
    }


}