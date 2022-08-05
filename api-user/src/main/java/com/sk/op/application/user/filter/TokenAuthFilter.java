package com.sk.op.application.user.filter;

import com.sk.op.application.user.component.*;
import com.sk.op.application.user.handler.TokenAuthHandler;
import com.sk.op.application.user.service.TokenService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final TokenService tokenService;
    private final TokenAuthHandler tokenAuthHandler;

    private final UserAuthDetailsSource authDetailsSource = new UserAuthDetailsSource();

    public TokenAuthFilter(TokenUtils tokenUtils, TokenService tokenService, TokenAuthHandler tokenAuthHandler) {
        this.tokenUtils = tokenUtils;
        this.tokenService = tokenService;
        this.tokenAuthHandler = tokenAuthHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenUtils.getTokenFromRequest(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserAuthDetails authDetails = authDetailsSource.buildDetails(request);

        try {
            UserDetails userDetails = tokenService.auth(token, authDetails);

            tokenAuthHandler.onAuthenticationSuccess(request, response, filterChain, new UserAuthToken(userDetails));
        } catch (AuthenticationException exception) {
            tokenAuthHandler.onAuthenticationFailure(request, response, exception);
        }
    }
}
