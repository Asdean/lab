package com.example.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lab.entity.Lab;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LabMapper extends BaseMapper<Lab> {

    @Select("select * from lab where name=#{name} or location=#{location}")
    Lab selectLabOr(@Param("name") String name, @Param("location") String location);

    @Select("select * from lab")
    List<Lab> selectLabsALl();

    @Select("select * from lab where name=#{name}")
    Lab selectLabByName(String name);

    @Select("select * from lab where location=#{location}")
    Lab selectLabByLocation(String location);

//    @Select("select * from lab where name=#{name} and location=#{location}")
//    Lab selectLabAnd(@Param("name") String name, @Param("location") String location);
}