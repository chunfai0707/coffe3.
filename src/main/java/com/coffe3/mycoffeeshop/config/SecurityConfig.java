package com.coffe3.mycoffeeshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@SuppressWarnings("unused")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select user_email, user_password from coffe3_user where user_email=?")
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                "/resources/**",
                "/static/**",
                "/css/**",
                "/fonts/**",
                "/images/**",
                "/js/**",
                "/plugins/**",
                "/scss/**",
                "/styles/**",
                "/vendor.jquery/**",
                "/", "/register", "/signup").permitAll();
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .loginPage("/login").permitAll()
//                    .and()
//                .logout().permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.sessionManagement().maximumSessions(2);
    }

}
