package annotation.dao;

import account.User;
import account.UserForAnnotationAlias;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/*通过注解,配置用户持久层接口*/
/*mybatis中mysql语句分为4类
* @Select @Insert @Update @Delete*/

//@CacheNamespace(blocking = true)      /*开启二级缓存*/      /*一级缓存默认开启*/
public interface IUserDao {


    /*sql语句*/
    @Select("select * from mybatis_test_01")
    List<User> findAll();
    /*<User>指定封装的方法*/


    @Insert("insert into mybatis_test_01 (username,sex,birthday,address) value (#{username},#{sex},#{birthday},#{address})")
    void createUser(User user);

    @Update("update mybatis_test_01 set username = #{username},sex = #{sex},birthday = #{birthday},address = #{address} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from mybatis_test_01 where id = #{id}")
    void deleteUser(Integer id);

    @Select("select * from mybatis_test_01 where username like  #{name}")
//    @Select("select * from mybatis_test_01 where username like '%${value}$%'")    /*或者可以使用非预处理语句的↓,不常用*/
    List<User> findUserByName(String keyWord);

    @Select("select * from mybatis_test_01 where id like  #{id}")
    User findUserById(Integer id);


    @Select("select count(id) from mybatis_test_01")
    Integer totalUsers();

//    ======================================================================================================

    @Select("select * from mybatis_test_01")
    /*使用@Results来启用别名,id为唯一标识符,可被其他查询语句通过@ResultMap获取(避免重复的@result代码)*/
    @Results(id = "userMapper",value={
            /*id:意为主键,只有一个*/
            @Result(id = true,property = "userId",column = "id"),
            @Result(property = "userName",column = "username"),
            @Result(property = "userSex",column = "sex"),
            @Result(property = "userBirthday",column = "birthday"),
            @Result(property = "userAddress",column = "address"),
    })
    List<UserForAnnotationAlias> findAllByAlias();


    /*用于演示ResultMap*/
    @Select("select * from mybatis_test_01 where username like  #{name}")
    @ResultMap(value = {"userMapper"})      /*标准写法(value可以省略)*/
    List<UserForAnnotationAlias> findUserByNameByAlias(String partOfName);


    @Select("select * from mybatis_test_01")
    @Results(id = "userAndAccountMapper",value={
            /*id:意为主键,只有一个*/
            @Result(id = true,property = "userId",column = "id"),
            @Result(property = "userName",column = "username"),
            @Result(property = "userSex",column = "sex"),
            @Result(property = "userBirthday",column = "birthday"),
            @Result(property = "userAddress",column = "address"),
            /*一对多的查询，格式基本与一对一相同*/
            @Result(property = "accountsForAnno",
                    column = "id",      /*指向id*/
                    many = @Many(select = "annotation.dao.IAccountDao.findAccountById",     /*为id选择查询方法*/
                    fetchType = FetchType.LAZY))        /*因为是一对多，选择延迟加载*/
    })
    List<UserForAnnotationAlias> findAllUserWithAccountByAlias();

}


