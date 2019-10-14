package account;

/*通过继承Account类的数据，再附加上Extend中的内容,用于与user类相连接*/
public class AccountExtend extends Account {
    private String username;
    private String address;

    @Override
    public String toString() {
        /*需要加上父类的toString，组合输出多表的结果*/
        return super.toString()+  "AccountExtend{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }
}
