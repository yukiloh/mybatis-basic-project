import account.Account;
import account.AccountExtend;
import account.User;
import dao.IAccountDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AccountTest {

    private IAccountDao mapper;
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
        mapper = sqlSession.getMapper(IAccountDao.class);

        /*上述过程后期可省略，但实际每个步骤分开使得使用更加灵活*/

        /*5.执行语句...*/
    }

    @After
    public void destroy() throws IOException {
        /*6.释放资源*/
        sqlSession.close();
        resourceAsStream.close();
    }



    /*一对多的查询*/
    @Test
    public void findAllInAccount2(){
        List<User> users = mapper.findAllInUser();
        for (User u:users) {
            System.out.println(u);
            System.out.println(u.getAccounts());
        }
    }


    /*查询所有*/
    @Test
    public void findAllInAccount(){
        List<Account> accounts = mapper.findAllInAccount();
        for (Account a:accounts) {
            System.out.println(a);      /*user对象已toString,可以获取*/
        }
    }



    /*一对一查询,让user类继承account类来实现多表查询*/
    @Test
    public void findAllInDoubleTable() {
        List<AccountExtend> accounts = mapper.findAllInDoubleTable();
        for (Account a : accounts) {
            System.out.println(a);
        }
    }

}


