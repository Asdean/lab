package com.example.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lab.entity.CourseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CourseRecordMapper extends BaseMapper<CourseRecord> {
}