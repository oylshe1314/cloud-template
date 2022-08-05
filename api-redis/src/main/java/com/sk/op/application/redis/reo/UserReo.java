package com.sk.op.application.redis.reo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@RedisHash
public class UserReo {

    @Id
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String token;

    @TimeToLive
    private Long expiration;
}
