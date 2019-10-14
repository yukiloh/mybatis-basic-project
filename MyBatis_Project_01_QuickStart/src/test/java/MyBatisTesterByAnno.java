import account.AccountForAnno;
import account.User;
import account.UserForAnnotationAlias;
import annotation.dao.IAccountDao;
import annotation.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


/*通过注解来演示执行SQL查询*/
public class MyBatisTesterByAnno {
    private IUserDao mapper;
    private IAccountDao mapperAccount;
    private SqlSession sqlSession;
    private InputStream resourceAsStream;


    @Before
    public void init() throws IOException {
        /*1.读取配置文件*/
        resourceAsStream = Resources.getResourceAsStream("mybatisConfigByAnno.xml");

        /*2.创建SqlSessionFactory工厂类*/
        /*创建 构建工厂类，mybatis使用的是构建者模式，省去繁琐的构建过程*/
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        /*创建 工厂类*/
        SqlSessionFactory build = sqlSessionFactoryBuilder.build(resourceAsStream);

        /*3.使用工厂类创建SqlSession对象*/   /*使用工厂模式生产sqlSession，降低耦合*/
        sqlSession = build.openSession();
        /*openSession中可以设置
        boolean autoCommit      自动提交,默认false
        Connection connection
        TransactionIsolationLevel level  事务等级
        ExecutorType execType*/

        /*4.使用SqlSession创建Dao接口的代理对象*/      /*使用了代理模式，增强方法*/
        mapper = sqlSession.getMapper(IUserDao.class);
        mapperAccount = sqlSession.getMapper(IAccountDao.class);

        /*上述过程后期可省略，但实际每个步骤分开使得使用更加灵活*/

        /*5.执行语句...*/
    }

    @After
    public void destroy() throws IOException {
        /*6.释放资源*/
        sqlSession.close();
        resourceAsStream.close();
    }



    /*===================================单表查询=============================================*/
    /*查询所有*/
    @Test
    public void findAll(){
        List<UserForAnnotationAlias> users = mapper.findAllByAlias();
        for (UserForAnnotationAlias u:users) {
            System.out.println(u);
        }

    }

    @Test
    public void createUser() throws IOException {
        /*定义一个user*/
        User user = new User();
        user.setUsername("mybatis_user05");
        user.setSex("man");
        user.setBirthday(new java.sql.Date(new Date().getTime()));  /*当天日期*/

        mapper.createUser(user);
        sqlSession.commit();            /*mybatis默认不自动提交，需要自行commit*/
//        System.out.println(user);       /*因为使用了selectKey，返回了id值，此时id不再是null*/
    }

    /*更新*/
    @Test
    public void updateUser() {
        User user = new User();
        user.setUsername("mybatis_user00");
        user.setSex("female");
        user.setBirthday(new java.sql.Date(new Date().getTime()));          /*当天日期*/
        user.setAddress("changed");
        user.setId(17);

        mapper.updateUser(user);

        sqlSession.commit();
    }

    /*删除*/
    @Test
    public void deleteUserById() {
        mapper.deleteUser(17);

        sqlSession.commit();
    }

    /*根据id查找*/
    @Test
    public void findUserById() {
        System.out.println(mapper.findUserById(1));
    }

    /*模糊查询,根据名字*/
    @Test
    public void findUserByName(){
        String partOfName = "my";
        List<User> users = mapper.findUserByName("%"+partOfName+"%");
        /*原Mapper中使用了like  #{name}，所以此处须提供%...%*/
        for (User u:users) {
            System.out.println(u);
        }
    }

    /*聚合查询，所有用户总数（根据id）*/
    @Test
    public void totalUsers(){
        int i = mapper.totalUsers();
        System.out.println("total users:"+i);
    }


    /*=====================================对应关系查询=======================================*/
    /*查询所有,通过别名*/       /*结果集使用另个实体类User→UserForAnnotationAlias*/
    @Test
    public void findAllByAlias(){
        List<UserForAnnotationAlias> usersAlias = mapper.findAllByAlias();
        for (UserForAnnotationAlias u:usersAlias) {
            System.out.println(u);
        }
    }

    /*模糊查找，通过别名*/
    @Test
    public void findUserByNameByAlias(){
        String partOfName = "my";
        List<UserForAnnotationAlias> usersAlias = mapper.findUserByNameByAlias("%"+partOfName+"%");
        for (UserForAnnotationAlias u:usersAlias) {
            System.out.println(u);
        }
    }

    /*=====================================多表查询=======================================*/
    /*备注：一个user可以对应多个account，一个account只能属于一个user*/
    /*查找Account表与User表的所有（一对一）*/
    @Test
    public void findAllForAccountWithUser(){
        List<AccountForAnno> accounts = mapperAccount.findAllWithUser();
        for (AccountForAnno a:accounts) {
            System.out.println(a);
            System.out.println(a.getUser());
        }
    }

    /*（一对多查询）*/
    @Test
    public void findAllForUserWithAccount(){
        List<UserForAnnotationAlias> users = mapper.findAllUserWithAccountByAlias();
        for (UserForAnnotationAlias u:users) {
            System.out.println(u);
            System.out.println(u.getAccountsForAnno());
        }
    }
}
