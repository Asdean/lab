package com.example.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lab.entity.CourseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseRecordMapper extends BaseMapper<CourseRecord> {

    @Select("select * from course_record where course_id=#{id}")
    List<CourseRecord> selectByCourseId(Long id);

    @Select("select * from course_record")
    List<CourseRecord> selectCourseRecordsALl();

    @Select("select * from course_record where course_id=#{cid}")
    List<CourseRecord> selectCourseRecordALl(Long cid);
}