package com.example.lab.controller.teacher;

import com.example.lab.entity.DTO.CourseDTO;
import com.example.lab.entity.DTO.LabDTO;
import com.example.lab.exception.MyException;
import com.example.lab.service.AdminService;
import com.example.lab.service.TeacherService;
import com.example.lab.vo.CRResponseVO;
import com.example.lab.vo.CourseRecordVO;
import com.example.lab.vo.CourseVO;
import com.example.lab.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lab/teacher/")
@Slf4j
public class CourseManageController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;

    // 添加课程，labId不能为空，为空应该默认传入0
    @ApiOperation("添加课程")
    @PostMapping("addCourse")
    public ResultVO addCourse(@Valid @RequestBody CourseVO courseVO) {
        log.debug(courseVO.getName());
        int rows = teacherService.saveCourse(courseVO);
        if (rows != 1) {
            throw new MyException(500, "服务器错误！");
        }
        return ResultVO.success(Map.of("msg", "课程添加成功！"));
    }

    // 查询课程列表
    @ApiOperation("查询课程列表")
    @GetMapping("listCourses")
    public ResultVO listCourses() {
        List<CourseDTO> courseDTOS = teacherService.selectCourses();
        return ResultVO.success(Map.of("courses", courseDTOS));
    }

    // 查询所有实验室
    @ApiOperation("查询实验室")
    @GetMapping("listLaboratory")
    public ResultVO listLaboratory() {
        List<LabDTO> labs = adminService.selectLabs();
        return ResultVO.success(Map.of("labs", labs));
    }

    // 预约实验室
    @ApiOperation("预约实验室")
    @PatchMapping("reservationLaboratory")
    public ResultVO reservationLaboratory(@RequestBody CourseDTO courseDTO) {
        int rows = teacherService.updateCourseLab(courseDTO);
        if (rows != 1) {
            throw new MyException(500, "服务器错误！");
        }
        return ResultVO.success(Map.of("msg", "课程预约实验室成功"));
    }

    // 删除课程
    @ApiOperation("删除课程")
    @DeleteMapping("deleteCourse/{cid}")
    public ResultVO deleteCourse(@PathVariable Long cid) {
        int rows = teacherService.deleteCourse(cid);
        if (rows != 1) {
            throw new MyException(500, "服务器错误");
        }
        return ResultVO.success(Map.of("msg", "实验室删除成功"));
    }

    // 添加课表
    @ApiOperation("添加课表")
    @PostMapping("addCourseRecord")
    public ResultVO addCourseRecord(@RequestBody CourseRecordVO courseRecordVO) {
        int rows = teacherService.addCourseRecord(courseRecordVO);
        if (rows < 1) {
            throw new MyException(500, "服务器错误！");
        }
        return ResultVO.success(Map.of("msg", "添加课表成功"));
    }

    // 查询某一课程课表
    @ApiOperation("根据课程id查询某一课程课表")
    @GetMapping("listCourseRecordById/{cid}")
    public ResultVO listCourseRecordById(@PathVariable Long cid) {
        List<CRResponseVO> lists = teacherService.listCourseRecordById(cid);
        return ResultVO.success(Map.of("course", lists));
    }

    // 查询课表
    @ApiOperation("查询所有课表")
    @GetMapping("listCourseRecord")
    public ResultVO listCourseRecord() {
        List<CRResponseVO> lists = teacherService.listCourseRecord();
        return ResultVO.success(Map.of("courses", lists));
    }
}
