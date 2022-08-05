package com.sk.op.application.user.service;

import com.sk.op.application.user.component.UserAuthDetails;
import com.sk.op.application.user.component.UserDetails;
import org.springframework.security.core.AuthenticationException;

public interface TokenService {

    UserDetails auth(String token, UserAuthDetails authDetails) throws AuthenticationException;

    String refresh(String token) throws AuthenticationException;
}
