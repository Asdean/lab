package com.example.lab.interceptor;

import com.example.lab.exception.MyException;
import com.example.lab.service.AdminService;
import com.example.lab.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long roleId = (Long) request.getAttribute("roleId");
        String roleName = adminService.getRoleName(roleId);
        //log.debug("{}", roleName);
        if (!("管理员".equals(roleName))) {
            throw new MyException(400, "权限不足");
        }
        return true;
    }
}
