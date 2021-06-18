package com.example.lab.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.lab.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JWTComponent {

    @Value("${my.salt}")
    private String salt;

    public String encode(Map<String, Object> map) {
        // 30min过期
        LocalDateTime time = LocalDateTime.now().plusMinutes(30);
        return JWT.create()
                .withPayload(map)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC256(salt));
    }

    public DecodedJWT decode(String token) {
        // log.debug("{}", token);
        try {
            return JWT.require(Algorithm.HMAC256(salt)).build().verify(token);
        } catch (TokenExpiredException | SignatureVerificationException e) {
            String msg = e instanceof TokenExpiredException
                    ? "过期请重新登录" : "无权限";
            throw new MyException(403, msg);
        }
    }
}
