package com.example.mybatis.quickstart.mapper.annotation;

import com.example.mybatis.quickstart.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 通过注解,配置用户持久层接口
 */
//@CacheNamespace(blocking = true)      //开启mybatis的二级缓存,一级缓存默认开启
@Mapper
public interface IUserMapper {

    /**
     * mybatis中crud语句分为4类:@Select @Insert @Update @Delete
     * 在注解的value中填入sql语句
     */

    @Select("select * from mybatis_user_table")
    List<User> findAll();   //通过泛型<User>指定返回的结果

    @Insert("insert into mybatis_user_table (username,sex,birthday,address) value (#{username},#{sex},#{birthday},#{address})")
    Integer createUser(User user);

    @Update("update mybatis_user_table set username = #{username},sex = #{sex},birthday = #{birthday},address = #{address} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from mybatis_user_table where id = #{id}")
    Integer deleteUser(Integer id);

    @Select("select * from mybatis_user_table where username like #{name}")
//    @Select("select * from mybatis_user_table where username like '%${value}$%'")    //或者可以使用非预处理语句,自行拼接匹配符%,不常用
    List<User> findUserByName(String keyWord);

    @Select("select * from mybatis_user_table where id = #{id}")
    User findUserById(Integer id);

    @Select("select count(id) from mybatis_user_table")
    Integer totalUsers();
}


