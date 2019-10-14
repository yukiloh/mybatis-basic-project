package account;

import java.util.List;

/*sql中角色role表的实体类*/
public class Role {
    private Integer roleId;
    private String roleName;
    private String roleDesc;

    /*生成多对多关系表*/
    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
//                "roleId=" + roleId +
                "roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
