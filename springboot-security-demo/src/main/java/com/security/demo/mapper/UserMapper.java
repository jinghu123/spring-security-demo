package com.security.demo.mapper;

import com.security.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    @Results({
           @Result(id = true,property = "id",column = "id"),
            @Result(property = "list",column = "id",javaType = List.class,
                    many = @Many(select = "com.security.demo.mapper.RoleMapper.getRolesByUserId")
            )
    })
    public User getUser(@Param("username") String username);
}
