package com.example.mybatis.quickstart.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

//user的实体类,其中属性取了别名
public class UserAlias implements Serializable {
    private Integer userId;
    private String userName;
    private String userSex;
    private Date userBirthday;
    private String userAddress;

    //用于演示一对多
    private List<Account> account;

    @Override
    public String toString() {
        return "UserAlias{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userBirthday=" + userBirthday +
                ", userAddress='" + userAddress + '\'' +
                ", account=" + account +
                '}';
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
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

    public UserAlias(Integer userId, String userName, String userSex, Date userBirthday, String userAddress) {
        this.userId = userId;
        this.userName = userName;
        this.userSex = userSex;
        this.userBirthday = userBirthday;
        this.userAddress = userAddress;
    }

    public UserAlias() {
    }
}