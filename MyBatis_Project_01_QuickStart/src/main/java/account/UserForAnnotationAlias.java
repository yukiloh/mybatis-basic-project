package account;

import java.io.Serializable;
import java.sql.Date;

/*用于提供注解配置的别名*/
public class UserForAnnotationAlias implements Serializable {
    private Integer userId;
    private String userName;
    private String userSex;
    private Date userBirthday;
    private String userAddress;
    /*用于演示一对多*/
    private AccountForAnno accountsForAnno;

    public AccountForAnno getAccountsForAnno() {
        return accountsForAnno;
    }

    public void setAccountsForAnno(AccountForAnno accountsForAnno) {
        this.accountsForAnno = accountsForAnno;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userBirthday=" + userBirthday +
                ", userAddress='" + userAddress + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public UserForAnnotationAlias(Integer userId, String userName, String userSex, Date userBirthday, String userAddress) {
        this.userId = userId;
        this.userName = userName;
        this.userSex = userSex;
        this.userBirthday = userBirthday;
        this.userAddress = userAddress;
    }

    public UserForAnnotationAlias() {
    }
}