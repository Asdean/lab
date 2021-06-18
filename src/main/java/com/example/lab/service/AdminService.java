package com.example.lab.service;

import com.example.lab.entity.DTO.UserDTO;
import com.example.lab.entity.Role;
import com.example.lab.entity.User;
import com.example.lab.exception.MyException;
import com.example.lab.mapper.RoleMapper;
import com.example.lab.mapper.UserMapper;
import com.example.lab.vo.AddUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AdminService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> selectUsers() {
        List<User> userList = userMapper.selectUsersAll();
        List<UserDTO> users = new ArrayList<>();
        for (User u : userList) {
            String roleName = getRoleName(u.getRoleId());
            UserDTO userDTO = UserDTO.builder()
                    .id(u.getId())
                    .username(u.getUsername())
                    .roleName(roleName)
                    .build();
            // log.debug("{}", userDTO.getId());
            users.add(userDTO);
        }
        return users;
    }

    // private int i = 0;

    @Cacheable(value = "roleName", key = "#roleId")
    public String getRoleName(Long roleId) {
        String roleName = roleMapper.selectById(roleId).getName();
        // log.debug("{}", i);
        // i++;
        return roleName;
    }

    private Long getRoleId(String roleName) {
        Long roleId = roleMapper.selectRoleIdByRoleName(roleName);
        return roleId;
    }

    public int updateUserRole(UserDTO userDTO) {
        Long roleId = getRoleId(userDTO.getRoleName());
        if (roleId == null) {
            throw new MyException(400, "要修改的权限不存在！");
        }
        User user = User.builder()
                .id(userDTO.getId())
                .roleId(roleId).build();
        return userMapper.updateById(user);
    }

    public int addUser(AddUserVO userVO) {
        Long roleId = getRoleId(userVO.getRoleName());
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new MyException(400, "用户角色名称错误");
        }
        String password = passwordEncoder.encode(userVO.getPassword());
        User u = User.builder()
                .username(userVO.getUsername())
                .password(password)
                .roleId(roleId).build();
        int rows = userMapper.insert(u);
        return rows;
    }

    public int deleteUser(Long id) {
        int rows = userMapper.deleteById(id);
        return rows;
    }
}
