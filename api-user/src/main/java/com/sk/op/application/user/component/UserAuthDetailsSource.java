package com.sk.op.application.user.component;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

public class UserAuthDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, UserAuthDetails> {

    @Override
    public UserAuthDetails buildDetails(HttpServletRequest context) {
        return new UserAuthDetails(context);
    }
}
