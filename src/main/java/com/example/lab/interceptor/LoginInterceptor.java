package com.example.lab.interceptor;

import com.example.lab.exception.MyException;
import com.example.lab.security.JWTComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTComponent jwtComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            throw new MyException(401, "未登录");
        }
        // log.debug("{}", token);
        Long roleId = jwtComponent.decode(token).getClaim("roleId").asLong();
        request.setAttribute("roleId", roleId);
        return true;
    }
}
