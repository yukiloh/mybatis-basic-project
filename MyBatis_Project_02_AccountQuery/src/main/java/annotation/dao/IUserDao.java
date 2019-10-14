package annotation.dao;     /*全限定类名*/

import account.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*注解配置,用户持久层接口*/
public interface IUserDao{  /*全限定类名2*/


    /*sql语句*/
    @Select("select * from mybatis_test_01")
    List<User> findAll();
    /*<User>指定封装的方法*/

}


