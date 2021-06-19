package com.example.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lab.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select * from course where name=#{name}")
    Course selectByCourseName(String name);

    @Select("select * from course where lab_id=#{labId}")
    Course selectByLabId(Long labId);

    @Select("select * from course")
    List<Course> selectCoursesAll();
}