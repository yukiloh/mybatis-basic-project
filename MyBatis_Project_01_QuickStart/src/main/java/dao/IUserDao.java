package dao;


import account.QueryVo;
import account.User;

import java.util.List;

/*用户持久层接口*/
public interface IUserDao {
    /*查询所有*/
    List<User> findAll();

    /*查询单个*/
    List<User> findUserById(Integer id);


    /*通过Vo对象,模糊查询*/
    List<User> findUserByVo(QueryVo queryVo);


    /*模糊查询*/
    List<User> findUserByName(String name);

    /*查询用户总数*/
    int totalUsers();

    /*创建*/
    void createUser(User user);

    /*更新*/
    void updateUser(User user);

    /*删除*/
    void deleteUser(Integer id);

    /*根据条件查询,用于动态查询*/
    List<User> findUserByCondition(User user);

    /*使用动态查询,in()的用法*/
    List<User> findUserByInIdentify(QueryVo queryVo);

}
