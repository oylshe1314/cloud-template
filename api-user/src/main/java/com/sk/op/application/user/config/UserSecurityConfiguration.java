package com.sk.op.application.user.config;

import com.sk.op.application.user.component.TokenUtils;
import com.sk.op.application.user.filter.TokenAuthFilter;
import com.sk.op.application.user.handler.TokenAuthHandler;
import com.sk.op.application.user.handler.UserAccessDeniedHandler;
import com.sk.op.application.user.service.TokenService;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {UserDetailsServiceAutoConfiguration.class})
public class UserSecurityConfiguration {

    private final TokenUtils tokenUtils;
    private final TokenService tokenService;
    private final TokenAuthHandler tokenAuthHandler;
    private final UserAccessDeniedHandler userAccessDeniedHandler;

    public UserSecurityConfiguration(TokenUtils tokenUtils, TokenService tokenService, TokenAuthHandler tokenAuthHandler, UserAccessDeniedHandler userAccessDeniedHandler) {
        this.tokenUtils = tokenUtils;
        this.tokenService = tokenService;
        this.tokenAuthHandler = tokenAuthHandler;
        this.userAccessDeniedHandler = userAccessDeniedHandler;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.httpBasic().authenticationEntryPoint(userAccessDeniedHandler);
        httpSecurity.exceptionHandling().accessDeniedHandler(userAccessDeniedHandler);
        httpSecurity.authorizeRequests().antMatchers("/doc/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated();

        httpSecurity.addFilterAt(tokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private TokenAuthFilter tokenAuthFilter() {
        return new TokenAuthFilter(tokenUtils, tokenService, tokenAuthHandler);
    }
}
