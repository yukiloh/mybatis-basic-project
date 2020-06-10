package com.example.mybatis.quickstart.mapper.annotation;

import com.example.mybatis.quickstart.entity.UserAlias;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 别名查询,演示了2个案例
 */
@Mapper
public interface IUserAliasMapper {

    /**
     * 案例1.查询带别名的表
     * 当遇到实体类与数据库字段不匹配时,也可以使用 @Results 来定义别名
     */
    @Select("select * from mybatis_user_table")
    @Results(   //使用@Results来启用别名.其中id为唯一标识符,可被其他查询语句通过@ResultMap获取(以避免重复使用@result)
        id = "userMapper"
        ,value={
            @Result(id = true,property = "userId",column = "id"),   //id:数据库中的主键(只有一个)
            @Result(property = "userName",column = "username"),
            @Result(property = "userSex",column = "sex"),
            @Result(property = "userBirthday",column = "birthday"),
            @Result(property = "userAddress",column = "address")
        }
    )
    List<UserAlias> findAllByAlias();

    @Select("select * from mybatis_user_table where username like  #{name}")
    @ResultMap(value = {"userMapper"})                            //使用@ResultMap来使用已经定义映射关系
    List<UserAlias> findUserByNameByAlias(String partOfName);

    /**
     * 案例2.查询(带别名且)包含一对多关系的结果集
     * @return
     */
    @Select("select * from mybatis_user_table")
    @Results(id = "userAndAccountMapper",value={
            @Result(id = true,property = "userId",column = "id"),
            @Result(property = "userName",column = "username"),
            @Result(property = "userSex",column = "sex"),
            @Result(property = "userBirthday",column = "birthday"),
            @Result(property = "userAddress",column = "address"),
            @Result(property = "account",                       //一对多的查询，格式基本与一对一相同
                    column = "id",                              //col指向id,用于提供"多"查询的属性
                    many =
                    @Many(select =                              //选择"多"查询的方法
                            "com.example.mybatis.quickstart.mapper.annotation.IAccountMapper.findAccountByUid"
                            ,fetchType = FetchType.LAZY         //因为是一对多，选择延迟加载
                    )
            )
    })
    List<UserAlias> findAllUserWithAccountByAlias();

}


