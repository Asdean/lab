package com.example.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lab.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user u where u.username=#{username}")
    User selectByUsername(String username);

    @Select("select id,username,role_id from user")
    List<User> selectUsersAll();
}