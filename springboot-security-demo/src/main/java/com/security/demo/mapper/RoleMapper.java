package com.security.demo.mapper;

import com.security.demo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("select role_name as roleName,role_desc as roleDesc from role,role_to_user where role.id = role_to_user.role_id and user_id = #{userId}")
    public List<Role> getRolesByUserId(String userId);
}
