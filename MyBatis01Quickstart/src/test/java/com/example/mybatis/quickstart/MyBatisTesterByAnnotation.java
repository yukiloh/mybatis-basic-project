package com.example.mybatis.quickstart;


import com.example.mybatis.quickstart.entity.Account;
import com.example.mybatis.quickstart.entity.UserAlias;
import com.example.mybatis.quickstart.entity.User;
import com.example.mybatis.quickstart.mapper.annotation.IAccountMapper;
import com.example.mybatis.quickstart.mapper.annotation.IUserAliasMapper;
import com.example.mybatis.quickstart.mapper.annotation.IUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


/**
 * 通过注解来演示执行SQL查询
 */
public class MyBatisTesterByAnnotation {
    private IUserMapper userMapper;
    private IAccountMapper accountMapper;
    private IUserAliasMapper userAliasMapper;
    private SqlSession sqlSession;
    private InputStream resourceAsStream;

    /**
     * 初始化mybatis
     */
    @Before
    public void init() throws IOException {
        //1.读取配置文件
        resourceAsStream = Resources.getResourceAsStream("config/mybatisConfigByAnnotation.xml");

        //2.创建SqlSessionFactory工厂类
        //创建 构建工厂类，mybatis使用构建者模式，省去繁琐的构建过程
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //创建 工厂类
        SqlSessionFactory build = sqlSessionFactoryBuilder.build(resourceAsStream);

        //3.使用工厂类创建SqlSession对象.mybatis使用工厂模式生产sqlSession，降低耦合
        //openSession中可以设置:
        //boolean autoCommit                自动提交,默认false
        //Connection connection
        //TransactionIsolationLevel level   事务等级
        //ExecutorType execType
        sqlSession = build.openSession();

        //4.使用SqlSession创建Dao接口的代理对象(通过代理模式来增强方法)
        userMapper = sqlSession.getMapper(IUserMapper.class);
        accountMapper = sqlSession.getMapper(IAccountMapper.class);
        userAliasMapper = sqlSession.getMapper(IUserAliasMapper.class);

        //上述过程后期可省略，但实际每个步骤分开使得使用更加灵活

        //5.执行语句...
    }

    /**
     * 关闭连接
     */
    @After
    public void destroy() throws IOException {
        //6.释放资源
        sqlSession.close();
        resourceAsStream.close();
    }


    /**
     * ====基础查询===
     * 查询所有
     */
    @Test
    public void findAll(){
        List<UserAlias> users = userAliasMapper.findAllByAlias();
        for (UserAlias u:users) {
            System.out.println(u);
        }
    }

    /**
     * 创建user
     */
    @Test
    public void createUser() throws IOException {
        //需要定义一个user
        User user = new User();
        user.setUsername("mybatis_user03");
        user.setSex("man");
        user.setBirthday(new Date());   //设置当前时间

        userMapper.createUser(user);
        sqlSession.commit();            //mybatis默认不自动提交，需要自行commit
//        System.out.println(user);       //因为使用了selectKey，返回了id值，此时id不再是null
    }

    /**
     * 更新用户
     */
    @Test
    public void updateUser() {
        User user = new User();
        user.setUsername("mybatis_user00");
        user.setSex("female");
        user.setBirthday(new java.sql.Date(new Date().getTime()));          //当天日期
        user.setAddress("changed");
        user.setId(17);

        userMapper.updateUser(user);
        sqlSession.commit();
    }

    //删除用户
    @Test
    public void deleteUserById() {
        Integer integer = userMapper.deleteUser(1);    //可以返回数据,0代表未找到,1代表找到,然后会删除
        sqlSession.commit();
        System.out.println(integer);
    }

    /**
     * 根据id查找
     */
    @Test
    public void findUserById() {
        System.out.println(userMapper.findUserById(1));
    }

    /**
     * 模糊查询,根据名字
     */
    @Test
    public void findUserByName(){
        String partOfName = "my";
        List<User> users = userMapper.findUserByName("%"+partOfName+"%");
        //原Mapper中使用了like  #{name}，所以此处须提供%...%
        for (User u:users) {
            System.out.println(u);
        }
    }

    /**
     * 聚合查询，所有用户总数（根据id）
     */
    @Test
    public void totalUsers(){
        int i = userMapper.totalUsers();
        System.out.println("total users:"+i);
    }

    /**
     * ====多表查询====
     * 查询所有account,并附带user的信息(多对一)
     */
    @Test
    public void findAllAccountWithUser(){
        List<Account> list = accountMapper.findAllAccountWithUser();
        for (Account account:list) {
            System.out.println(account);
        }
    }

    /**
     * 查询所有user,并附带account信息(一对多查询）
     */
    @Test
    public void findAllUserWithAccountByAlias(){
        List<UserAlias> users = userAliasMapper.findAllUserWithAccountByAlias();
        System.out.println("====================");
        users.forEach(userAlias -> System.out.println(userAlias));
        System.out.println("====================");
    }


    /**
     * ====别名查询(UserAlias)====
     * 通过别名来进行查询,使用了另一个实体类UserAlias
     */
    @Test
    public void findAllByAlias(){
        List<UserAlias> usersAlias = userAliasMapper.findAllByAlias();
        for (UserAlias u:usersAlias) {
            System.out.println(u);
        }
    }

}
