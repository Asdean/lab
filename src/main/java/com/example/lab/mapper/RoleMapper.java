package com.example.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lab.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from role where name=#{roleName}")
    Long selectRoleIdByRoleName(String roleName);

    @Select("select * from role where name=#{roleName}")
    Role selectByRoleName(String roleName);
}