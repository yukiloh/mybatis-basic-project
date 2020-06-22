
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
import java.util.List;

public class CacheTest {

    private IUserDao mapper;
    private SqlSession sqlSession;
    private InputStream resourceAsStream;
    private SqlSessionFactory build;


    @Before
    public void init() throws IOException {
        /*1.读取配置文件*/
        resourceAsStream = Resources.getResourceAsStream("Cache.mybatisConfig.xml");

        /*2.创建SqlSessionFactory工厂类*/
        /*创建 构建工厂类，mybatis使用的是构建者模式，省去繁琐的构建过程*/
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        /*创建 工厂类*/
        build = sqlSessionFactoryBuilder.build(resourceAsStream);

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



    /*测试1级缓存*/
    /*分析：
      适用于缓存的情况：经常查询且不改变的数据，数据正确与否对结果影响不大
      不适用于缓存的情景：商品的库存、实时汇率、股市牌价*/
    @Test
    public void findByIdWithCacheI(){
        List<User> users1 = mapper.findUserById(1);

//        /*演示一级缓存的功能，关闭session*/
//        sqlSession.close();
//        /*再次打开*/
//        build.openSession();
//        /*此时2次获取的对象为不相同*/
//        /*或者可以采用清空缓存*/
//        sqlSession.clearCache();
//        /*当中间插入update语句时，mybatis会立刻清空缓存！（省略演示）*/

        List<User> users2 = mapper.findUserById(1);
        if (users1 == users2) System.out.println("true");
        /*只执行一次查询，所以对象为同一个*/
    }


    /*测试二级缓存*/
    @Test
    public void findByIdWithCacheII(){
        /*开启二级缓存的步骤：
         * 1.config.xml中,开启mybatis框架对二级缓存的支持
         * 2.mapper.xml中,开启映射文件对二级缓存的支持
         * 3.使语句标签,开启对二级缓存的支持*/

        SqlSession sqlSession1 = build.openSession();
        IUserDao mapper1 = sqlSession1.getMapper(IUserDao.class);
        List<User> users1 = mapper1.findUserById(1);
        sqlSession1.close();

        SqlSession sqlSession2 = build.openSession();
        IUserDao mapper2 = sqlSession2.getMapper(IUserDao.class);
        List<User> users2 = mapper2.findUserById(1);
        sqlSession1.close();

        if (users1 == users2) {System.out.println("true");}else System.out.println("false");
        /*最后结果以然为false，但是查询只发起一次。
        * 原因在于二级缓存开启后，结果集储存于二级缓存中，session第二次调用后以然会创建新的对象*/
    }
}


