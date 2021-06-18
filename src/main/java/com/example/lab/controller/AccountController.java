package com.example.lab.controller;

import com.example.lab.entity.User;
import com.example.lab.security.JWTComponent;
import com.example.lab.service.AccountService;
import com.example.lab.vo.AddUserVO;
import com.example.lab.vo.LoginVO;
import com.example.lab.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/lab/")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTComponent jwtComponent;

    @ApiOperation("注册账号")
    @PostMapping("teacher/register")
    public ResultVO addTeacher(@Valid @RequestBody AddUserVO userVO) {
        int rows = accountService.saveUser(userVO);
        if (rows != 1) {
            return ResultVO.error(500, "注册失败，服务器异常！");
        }
        return ResultVO.success(Map.of("username", userVO.getUsername()));
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public ResultVO teacherLogin(@RequestBody LoginVO loginVO, HttpServletResponse response) {
            User u = accountService.getUser(loginVO.getUsername());
            if (u == null || !passwordEncoder.matches(loginVO.getPassword(), u.getPassword())) {
                return ResultVO.error(401, "用户名或密码错误");
            }
            // 登录成功，模拟获取用户id角色等信息，加密
            String token = jwtComponent.encode(Map.of("userId", u.getId(), "roleId", u.getRoleId()));
            // 以指定键值对，置于响应header
            response.addHeader("token", token);
            return ResultVO.success(Map.of());

    }
}
