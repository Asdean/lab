package com.example.lab.service;

import com.example.lab.entity.DTO.LabDTO;
import com.example.lab.entity.DTO.UserDTO;
import com.example.lab.entity.Lab;
import com.example.lab.entity.Role;
import com.example.lab.entity.User;
import com.example.lab.exception.MyException;
import com.example.lab.mapper.LabMapper;
import com.example.lab.mapper.RoleMapper;
import com.example.lab.mapper.UserMapper;
import com.example.lab.vo.AddUserVO;
import com.example.lab.vo.LabVO;
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
    @Autowired
    private LabMapper labMapper;

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

    @Cacheable(value = "roleId", key = "#roleName")
    public Long getRoleId(String roleName) {
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
        User user = userMapper.selectByUsername(userVO.getUsername());
        if (user != null) {
            throw new MyException(400, "用户名已存在");
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

    public Lab selectLabOr(String name, String location) {
        return labMapper.selectLabOr(name, location);
    }

//     private Lab selectLabAnd(String name, String location) {
//        return labMapper.selectLabAnd(name, location);
//     }

    @Cacheable(value = "lab", key = "#id")
    public Lab selectLabById(Long id) {
        return labMapper.selectById(id);
    }

    @Cacheable(value = "lab", key = "#name")
    public Lab selectLabByName(String name) {
        return labMapper.selectLabByName(name);
    }

    @Cacheable(value = "lab", key = "#location")
    public Lab selectLabByLocation(String location) {
        return labMapper.selectLabByLocation(location);
    }

    public int addLaboratory(LabVO labVO) {
        // log.debug("{}", labVO.getName());
        Lab lab = selectLabOr(labVO.getName(), labVO.getLocation());
        if (lab != null) {
            throw new MyException(400, "实验室已存在或实验室位置已占用！");
        }
        Lab laboratory = Lab.builder()
                .name(labVO.getName())
                .location(labVO.getLocation())
                .number(labVO.getNumber())
                .memo(labVO.getMemo()).build();
        int rows = labMapper.insert(laboratory);
        return rows;
    }


    public List<LabDTO> selectLabs() {
        List<Lab> labList = labMapper.selectLabsALl();
        List<LabDTO> lists = new ArrayList<>();
        for (Lab l : labList) {
            LabDTO lab = LabDTO.builder()
                    .id(l.getId())
                    .name(l.getName())
                    .location(l.getLocation())
                    .number(l.getNumber())
                    .memo(l.getMemo())
                    .build();
            lists.add(lab);
        }
        return lists;
    }

    public int updateLab(LabDTO lab) {
        Lab lab1 = selectLabById(lab.getId());
        // log.debug(lab1.getName());
        // log.debug(lab.getName());
        // log.debug(lab1.getLocation());
        // log.debug("{}", lab1.getName() == lab.getName());
        if (!(lab1.getName().equals(lab.getName())) && !(lab1.getLocation().equals(lab.getLocation()))) {
            Lab lab2 = selectLabOr(lab.getName(), lab.getLocation());
            if (lab2.getId() != lab.getId()) {
                throw new MyException(400, "实验室名称和实验室位置与其他实验室冲突！");
            }
        }
        if (!(lab1.getName().equals(lab.getName()))) {
            Lab lab2 = selectLabByName(lab.getName());
            if (lab2 == null) {
                Lab laboratory = Lab.builder()
                        .id(lab.getId())
                        .name(lab.getName())
                        .location(lab.getLocation())
                        .number(lab.getNumber())
                        .memo(lab.getMemo()).build();
                int rows = labMapper.updateById(laboratory);
                return rows;
            }
            if (lab2.getId() != lab.getId()) {
                throw new MyException(400, "实验室名称与其他实验室冲突！");
            }
        }
        if (!(lab1.getLocation().equals(lab.getLocation()))) {
            Lab lab2 = selectLabByLocation(lab.getLocation());
            if (lab2 == null) {
                Lab laboratory = Lab.builder()
                        .id(lab.getId())
                        .name(lab.getName())
                        .location(lab.getLocation())
                        .number(lab.getNumber())
                        .memo(lab.getMemo()).build();
                int rows = labMapper.updateById(laboratory);
                return rows;
            }
            if (lab2.getId() != lab.getId()) {
                throw new MyException(400, "实验室位置与其他实验室冲突！");
            }
        }
        Lab laboratory = Lab.builder()
                .id(lab.getId())
                .name(lab.getName())
                .location(lab.getLocation())
                .number(lab.getNumber())
                .memo(lab.getMemo()).build();
        int rows = labMapper.updateById(laboratory);
        return rows;
    }

    public int deleteLab(Long id) {
        int rows = labMapper.deleteById(id);
        return rows;
    }
}
