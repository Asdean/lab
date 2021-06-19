package com.example.lab.service;

import com.example.lab.entity.Role;
import com.example.lab.entity.User;
import com.example.lab.exception.MyException;
import com.example.lab.mapper.RoleMapper;
import com.example.lab.mapper.UserMapper;
import com.example.lab.vo.AddUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class AccountService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    // 添加用户
    public int saveUser(AddUserVO userVO) {
        if (userVO.getRoleName() != null) {
            if (!("教师".equals(userVO.getRoleName()))) {
                throw new MyException(400, "用户只能注册角色为教师的账号！");
            }
            Role role = getRoleByRoleName(userVO.getRoleName());
            if (role == null) {
                throw new MyException(400, "注册角色不存在！");
            }
        }
        String password = passwordEncoder.encode(userVO.getPassword());
        User u = User.builder()
                .username(userVO.getUsername())
                .password(password)
                .build();
        return userMapper.insert(u);
    }

    // 根据用户角色名称获取角色信息
    public Role getRoleByRoleName(String roleName) {
        return roleMapper.selectByRoleName(roleName);
    }

    // 根据用户名获取用户
    public User getUser(String username) {
        User user = userMapper.selectByUsername(username);
        return user;
    }
}
