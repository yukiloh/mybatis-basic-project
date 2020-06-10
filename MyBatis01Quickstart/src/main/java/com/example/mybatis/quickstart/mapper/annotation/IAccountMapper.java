package com.example.mybatis.quickstart.mapper.annotation;

import com.example.mybatis.quickstart.entity.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * User下的Account账户的dao接口类.与user形成多对一的关系
 */
@Mapper
public interface IAccountMapper {

    //查找account下的所有账户，并映射所对应的user
    @Select("SELECT * FROM mybatis_account_table")
    @Results(
        id = "accountMapper"
        ,value={
        @Result(id = true,property = "id",column = "id"),
        @Result(property = "uid",column = "uid"),
        @Result(property = "money",column = "money"),
        @Result(                                          //查询account所属的user(对一的查询)
                property = "user"                         //实体类中的属性名
                ,column = "uid"                           //指定需要查询的值(为one提供值)
                ,one = @One(                              //进行"对一"的查询,select指定查询方法
                        select = "com.example.mybatis.quickstart.mapper.annotation.IUserMapper.findUserById"
                        ,fetchType = FetchType.EAGER      //因为是对一查询,需要立即加载(对多则是延迟加载)
                )
        ),
    })
    List<Account> findAllAccountWithUser();

    @Select("select * from mybatis_account_table where uid = #{uid}")
    List<Account> findAccountByUid(Integer uid);




}
