package dao;

import account.Role;
import account.User;

import java.util.List;

public interface IRoleDao {

    /*查询role表的所有角色*/
    List<Role> findAllRole();

    List<Role> findAllRoleWithUser();

    List<User> findAllUserWithRole();
}
