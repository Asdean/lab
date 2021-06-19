package com.example.lab.service;

import com.example.lab.entity.Course;
import com.example.lab.entity.DTO.CourseDTO;
import com.example.lab.entity.Lab;
import com.example.lab.exception.MyException;
import com.example.lab.mapper.CourseMapper;
import com.example.lab.mapper.LabMapper;
import com.example.lab.vo.CourseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TeacherService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private LabMapper labMapper;

    @Cacheable(value = "course", key = "#name")
    public Course selectByCourseName(String name) {
        return courseMapper.selectByCourseName(name);
    }

    @Cacheable(value = "course", key = "#labId")
    public Course selectByLabId(Long labId) {
        return courseMapper.selectByLabId(labId);
    }

    @Cacheable(value = "lab", key = "#id")
    public Lab selectLabById(Long id) {
        return labMapper.selectById(id);
    }

    @Cacheable(value = "labName", key = "#id")
    public String getLabNameByLabId(Long id) {
        String labName = labMapper.selectById(id).getName();
        return labName;
    }

    @Cacheable(value = "course", key = "#id")
    public Course selectCourseById(Long id) {
        Course course = courseMapper.selectById(id);
        return course;
    }

    @Cacheable(value = "labId", key = "#labName")
    public Long selectLabIdByLabName(String labName) {
        Long labId = labMapper.selectLabByName(labName).getId();
        return labId;
    }


    public int saveCourse(CourseVO courseVO) {
        Course course1 = selectByCourseName(courseVO.getName());
        if (course1 != null) {
            throw new MyException(401, "课程名称与其他名称重复！");
        }
        if (courseVO.getLabId() == null || courseVO.getLabId() == 0) {
            Course c = Course.builder()
                    .name(courseVO.getName())
                    .studentAmount(courseVO.getStudentAmount())
                    .memo(courseVO.getMemo()).build();
            int rows = courseMapper.insert(c);
            return rows;
        }
        Course course2 = selectByLabId(courseVO.getLabId());
        if (course2 != null) {
            throw new MyException(401, "实验室已经占用！");
        }
        Lab lab = selectLabById(courseVO.getLabId());
        if (lab.getNumber() < courseVO.getStudentAmount()) {
            throw new MyException(400, "实验室可容纳人数小于课程人数！");
        }
        Course c = Course.builder()
                .name(courseVO.getName())
                .studentAmount(courseVO.getStudentAmount())
                .labId(courseVO.getLabId())
                .memo(courseVO.getMemo()).build();
        int rows = courseMapper.insert(c);
        return rows;
    }

    public List<CourseDTO> selectCourses() {
        List<Course> courses = courseMapper.selectCoursesAll();
        List<CourseDTO> lists = new ArrayList<>();
        for (Course c : courses) {
            if (c.getLabId() == 0) {
                CourseDTO courseDTO = CourseDTO.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .labName("")
                        .studentAmount(c.getStudentAmount())
                        .memo(c.getMemo()).build();
                lists.add(courseDTO);
                continue;
            }
            String labName = getLabNameByLabId(c.getLabId());
            CourseDTO courseDTO = CourseDTO.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .labName(labName)
                    .studentAmount(c.getStudentAmount())
                    .memo(c.getMemo()).build();
            lists.add(courseDTO);
        }
        return lists;
    }

    public int updateCourseLab(CourseDTO courseDTO) {
        Course course = selectCourseById(courseDTO.getId());
        if (course.getLabId() != 0) {
            throw new MyException(400, "课程已有实验室");
        }
        Long labId = selectLabIdByLabName(courseDTO.getLabName());
        Course course2 = selectByLabId(labId);
        if (course2 != null) {
            throw new MyException(401, "实验室已经占用！");
        }
        Course c = Course.builder()
                .id(courseDTO.getId())
                .name(courseDTO.getName())
                .studentAmount(courseDTO.getStudentAmount())
                .labId(labId)
                .memo(course.getMemo()).build();
        int rows = courseMapper.updateById(c);
        return rows;
    }

    public int deleteCourse(Long id) {
        int rows = courseMapper.deleteById(id);
        return rows;
    }
}
