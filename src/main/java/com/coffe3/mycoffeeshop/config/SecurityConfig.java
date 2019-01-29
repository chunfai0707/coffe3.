package com.coffe3.mycoffeeshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    private final String USER_QUERY = "select user_email, user_password, user_enabled from coffe3_user where user_email=?";
    private final String ROLE_QUERY = "SELECT u.user_email, r.role_name " +
            "FROM coffe3_user u " +
            "INNER JOIN coffe3_user_role ur ON (u.user_id = ur.user_id) " +
            "INNER JOIN coffe3_role r ON (ur.role_id=r.role_id) " +
            "WHERE u.user_email=?";

    private final String[] RESOURCES = new String[]{"/", "/register", "/signup", "/coffee/**", "/product", "/subscribe", "/static/**", "/css/**", "/fonts/**", "/images/**", "/js/**", "/plugins/**", "/scss/**", "/styles/**", "/vendor.jquery/**"};

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(ROLE_QUERY)
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(RESOURCES).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/accessdenied")
                .usernameParameter("userEmail")
                .passwordParameter("userPassword")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        redirectStrategy.sendRedirect(request, response, "/");
                    }
                })
                .and()
                .logout().logoutSuccessUrl("/")
                .logoutUrl("logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and().exceptionHandling().accessDeniedPage("/accessdenied");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.sessionManagement().maximumSessions(2);
    }
}
