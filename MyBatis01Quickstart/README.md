# mybatis 的快速入门使用

过去的项目,注解部分会一定翻新,xml配置部分大概率放弃  
使用注解的演示入口在测试类 `MyBatisTesterByAnnotation` 中  

## 项目结构

```
 src
 |-- main
 |   |-- java
 |   |   |-- com
 |   |       |-- example
 |   |           |-- mybatis
 |   |               |-- quickstart
 |   |                   |-- entity
 |   |                   |   |-- Account.java               //账户实体类,一个用户对应多个账户
 |   |                   |   |-- QueryVo.java               //动态查询相关,没有翻新
 |   |                   |   |-- User.java                  //用户实体类
 |   |                   |   |-- UserAlias.java             //取了别名的实体类
 |   |                   |-- mapper
 |   |                       |-- annotation                 //该包下都是通过注解实现的mapper
 |   |                       |   |-- IAccountMapper.java    //account的mapper
 |   |                       |   |-- IUserAliasMapper.java  //取了别名的userMapper
 |   |                       |   |-- IUserMapper.java       
 |   |                       |-- xml
 |   |                           |-- IUserMapper.java       //通过xml配置实现的mapper
 |   |-- resources                                          
 |       |-- jdbcConfig.properties
 |       |-- log4j.properties.bk
 |       |-- log4j2.xml
 |       |-- config
 |       |   |-- mybatisConfig.xml
 |       |   |-- mybatisConfigByAnnotation.xml              //用于给使用注解的mapper的配置类
 |       |   |-- mybatisConfigByDynamicQuery.xml
 |       |-- mapper
 |           |-- mybatisMapper.xml
 |           |-- mybatisMapperByDynamicQuery.xml
 |-- test
     |-- java
         |-- com
             |-- example
                 |-- mybatis
                     |-- quickstart
                         |-- MyBatisTester.java
                         |-- MyBatisTesterByAnnotation.java     //通过注解实现的mybatis调用方法演示
                         |-- MyBatisTesterByDynamicQuery.java  

```
