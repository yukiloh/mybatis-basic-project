import account.QueryVo;
import account.User;
import dao.IUserDao;
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



/*后期补充：@Param注解、模糊查询用CONCAT、以及组合模式的实体、xml里${}方式的安全性、mybatis插件、mybatis拦截器*/
public class MyBatisTester {

    private IUserDao mapper;
    private SqlSession sqlSession;
    private InputStream resourceAsStream;


    @Before
    public void init() throws IOException {
        /*1.读取配置文件*/
        resourceAsStream = Resources.getResourceAsStream("mybatisConfig.xml");

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

        /*上述过程后期可省略，但实际每个步骤分开使得使用更加灵活*/

        /*5.执行语句...*/
    }

    @After
    public void destroy() throws IOException {
        /*6.释放资源*/
        sqlSession.close();
        resourceAsStream.close();
    }


    /*查询所有*/
    @Test
    public void findAll(){
        /*5.使用代理对象执行方法实现增删改查*/
        List<User> users = mapper.findAll();
        for (User u:users) {
            System.out.println(u);
        }
    }

    /*根据id*/
    @Test
    public void findUserById(){
        List<User> users = mapper.findUserById(1);
        if (users.size() == 1){
            System.out.println(users.get(0));
        }else {
            System.out.println("error!");
        }
    }

    /*模糊查询,根据名字*/
    @Test
    public void findUserByName(){
        String partOfName = "ad";
        List<User> users = mapper.findUserByName("%"+partOfName+"%");
        /*原Mapper中使用了like  #{name}，所以此处必须提供%...%*/
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

    /*模糊查询,根据Vo*/
    @Test
    public void findUserByVo(){
        User user = new User();       /*创建user对象*/
        user.setUsername("%ad%");     /*设置需要查询的名字,因为queryVo无法提供% %所以必须在此处插入模糊关键词*/
        QueryVo queryVo = new QueryVo();    /*创建vo*/
        queryVo.setUser(user);              /*将user传入,提供username值*/
        List<User> users = mapper.findUserByVo(queryVo);    /*执行查询*/
        for (User u:users) {
            System.out.println(u);
        }
    }









    /*创建用户*/
    @Test
    public void createUser() throws IOException {
        /*定义一个user*/
        User user = new User();
        user.setUsername("mybatis_user02");
        user.setSex("man");
        user.setBirthday(new java.sql.Date(new Date().getTime()));  /*当天日期*/
        user.setAddress("unspecified");

        mapper.createUser(user);

        sqlSession.commit();            /*mybatis默认不自动提交，需要自行commit*/

        System.out.println(user);       /*因为使用了selectKey，返回了id值，此时id不再是null*/
    }


    /*更新*/
    @Test
    public void updateUser() {
        User user = new User();
        user.setUsername("mybatis_user01");
        user.setSex("man");
        user.setBirthday(new java.sql.Date(new Date().getTime()));          /*当天日期*/
        user.setAddress("changed");
        user.setId(12);

        mapper.updateUser(user);

        sqlSession.commit();
    }


    /*删除*/
    @Test
    public void deleteUser() {
        mapper.deleteUser(3);
        sqlSession.commit();
    }
}
