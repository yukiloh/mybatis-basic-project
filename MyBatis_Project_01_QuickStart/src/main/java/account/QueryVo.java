package account;


import java.util.List;

/*用作于vo的类,内部储存的是user对象*/
public class QueryVo {
    private User user;

    public List<Integer> getIdentify() {
        return identify;
    }

    public void setIdentify(List<Integer> identify) {
        this.identify = identify;
    }

    private List<Integer> identify; /*用于传递sql语句中的多个id*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
