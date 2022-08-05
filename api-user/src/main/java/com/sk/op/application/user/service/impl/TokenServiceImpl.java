package com.sk.op.application.user.service.impl;

import com.sk.op.application.common.dto.ResponseStatus;
import com.sk.op.application.common.exception.StandardRespondException;
import com.sk.op.application.redis.reo.UserReo;
import com.sk.op.application.redis.repository.UserReoRepository;
import com.sk.op.application.user.component.TokenUtils;
import com.sk.op.application.user.component.UserAuthDetails;
import com.sk.op.application.user.component.UserDetails;
import com.sk.op.application.user.service.TokenService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class TokenServiceImpl implements TokenService {

    private TokenUtils tokenUtils;
    private UserReoRepository userReoRepository;

    @Override
    public UserDetails auth(String token, UserAuthDetails authDetails) throws AuthenticationException {
        Long userId = tokenUtils.getUserId(token);

        UserReo userReo = userReoRepository.findById(userId).orElse(null);
        if (userReo == null) {
            throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.TOKEN_EXPIRED));
        }

        return new UserDetails(userReo);
    }

    @Override
    public String refresh(String token) throws AuthenticationException {
        Long userId = tokenUtils.getUserId(token, true);

        UserReo userReo = userReoRepository.findById(userId).orElse(null);
        if (userReo == null) {
            throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.TOKEN_INVALID));
        }

        token = tokenUtils.generateToken(userReo.getId(), userReo.getUsername());

        userReo.setToken(token);
        userReo.setExpiration(tokenUtils.getExpiration());

        userReoRepository.save(userReo);

        return token;
    }
}
