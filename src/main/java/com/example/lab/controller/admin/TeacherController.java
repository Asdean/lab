package com.example.lab.controller.admin;

import com.example.lab.entity.DTO.UserDTO;
import com.example.lab.exception.MyException;
import com.example.lab.service.AdminService;
import com.example.lab.vo.AddUserVO;
import com.example.lab.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lab/admin/")
public class TeacherController {

    @Autowired
    private AdminService adminService;

    // 管理员查询所有用户
    @ApiOperation("查询所有用户")
    @GetMapping("listUser")
    public ResultVO listUser() {
        List<UserDTO> users = adminService.selectUsers();
        return ResultVO.success(Map.of("users", users));
    }

    // 添加用户
    @ApiOperation("添加用户")
    @PostMapping("addTeacher")
    public ResultVO addUser(@Valid @RequestBody AddUserVO userVO) {
        int rows = adminService.addUser(userVO);
        if (rows != 1) {
            return ResultVO.error(500, "注册失败，服务器异常！");
        }
        return ResultVO.success(Map.of("username", userVO.getUsername()));
    }

    // 管理员修改用户权限
    @ApiOperation("修改用户信息")
    @PatchMapping("updateUserRole")
    public ResultVO updateUserRole(@RequestBody UserDTO userDTO) {
        int rows = adminService.updateUserRole(userDTO);
        if (rows != 1) {
            return ResultVO.error(500, "修改失败！");
        }
        return ResultVO.success(Map.of("username", userDTO.getUsername()));
    }

    // 删除用户
    @ApiOperation("根据用户主键删除用户")
    @DeleteMapping("deleteUser/{uid}")
    public ResultVO deleteUser(@PathVariable Long uid) {
        int rows = adminService.deleteUser(uid);
        if (rows != 1) {
            throw new MyException(400, "您所输入的用户不存在");
        }
        return ResultVO.success(Map.of());
    }
}
