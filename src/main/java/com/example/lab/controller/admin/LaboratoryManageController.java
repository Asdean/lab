package com.example.lab.controller.admin;

import com.example.lab.entity.DTO.LabDTO;
import com.example.lab.entity.Lab;
import com.example.lab.exception.MyException;
import com.example.lab.service.AdminService;
import com.example.lab.vo.LabVO;
import com.example.lab.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lab/admin/")
public class LaboratoryManageController {

    @Autowired
    private AdminService adminService;

    // 添加实验室
    @ApiOperation("添加实验室")
    @PostMapping("addLaboratory")
    public ResultVO addLaboratory(@Valid @RequestBody LabVO labVO) {
        int rows = adminService.addLaboratory(labVO);
        if (rows != 1) {
            throw new MyException(500, "服务器错误！");
        }
        return ResultVO.success(Map.of("msg", "实验室添加成功"));
    }

    // 查询所有实验室
    @ApiOperation("查询实验室")
    @GetMapping("listLaboratory")
    public ResultVO listLaboratory() {
        List<LabDTO> labs = adminService.selectLabs();
        return ResultVO.success(Map.of("labs", labs));
    }

    // 更改实验室信息
    @ApiOperation("更改实验室信息")
    @PatchMapping("updateLaboratory")
    public ResultVO updateLaboratory(@RequestBody LabDTO lab) {
        int rows = adminService.updateLab(lab);
        if (rows != 1) {
            throw new MyException(500, "服务器错误！");
        }
        return ResultVO.success(Map.of("msg", "实验室信息修改成功"));
    }

    // 删除实验室
    @ApiOperation("删除实验室")
    @DeleteMapping("deleteLaboratory/{lid}")
    public ResultVO deleteLaboratory(@PathVariable Long lid) {
        int rows = adminService.deleteLab(lid);
        if (rows != 1) {
            throw new MyException(400, "您所输入的实验室不存在");
        }
        return ResultVO.success(Map.of("msg", "实验室删除成功"));
    }
}
