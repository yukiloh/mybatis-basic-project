package com.example.mybatis.quickstart;

import com.example.mybatis.quickstart.entity.QueryVo;
import com.example.mybatis.quickstart.entity.User;
import com.example.mybatis.quickstart.mapper.xml.IUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 通过动态语句进行sql查询
 */
public class MyBatisTesterByDynamicQuery {

    private IUserMapper mapper;
    private SqlSession sqlSession;
    private InputStream resourceAsStream;


    @Before
    public void init() throws IOException {
        resourceAsStream = Resources.getResourceAsStream("config/mybatisConfigByDynamicQuery.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory build = sqlSessionFactoryBuilder.build(resourceAsStream);
        sqlSession = build.openSession();
        mapper = sqlSession.getMapper(IUserMapper.class);
    }

    @After
    public void destroy() throws IOException {
        sqlSession.close();
        resourceAsStream.close();
    }

    /**
     * 动态查询
     */
    @Test
    public void findByCondition(){
        User user = new User();
        user.setId(2);
        user.setUsername("%user%");       //模糊匹配,需要手动传入%

        List<User> users = mapper.findUserByCondition(user);
        if (users != null && users.size() != 0) {
            for (User u:users) {
                System.out.println(u);
            }
        }else System.out.println("no result!");
    }

    //动态查询,foreach的示范
    @Test
    public void findByInIdentify(){
        QueryVo queryVo = new QueryVo();
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        queryVo.setIdentify(arrayList);

        List<User> users = mapper.findUserByInIdentify(queryVo);
        if (users != null && users.size() != 0) {
            for (User u:users) {
                System.out.println(u);
            }
        }else System.out.println("no result!");

    }










    @Test
    public void findAll(){
        //5.使用代理对象执行方法实现增删改查
        List<User> users = mapper.findAll();
        for (User u:users) {
            System.out.println(u);
        }
    }

    @Test
    public void findUserById(){
        List<User> users = mapper.findUserById(1);
        if (users.size() == 1){
            System.out.println(users.get(0));
        }else {
            System.out.println("error!");
        }
    }

    //模糊查询,根据名字
    @Test
    public void findUserByName(){
        String partOfName = "ad";
        List<User> users = mapper.findUserByName("%"+partOfName+"%");
        //原Mapper中使用了like  #{name}，所以此处必须提供%...%
        for (User u:users) {
            System.out.println(u);
        }
    }


    //聚合查询，所有用户总数（根据id）
    @Test
    public void totalUsers(){
        int i = mapper.totalUsers();
        System.out.println("total users:"+i);
    }

    //模糊查询,根据Vo
    @Test
    public void findUserByVo(){
        User user = new User();       //创建user对象
        user.setUsername("%ad%");     //设置需要查询的名字,因为queryVo无法提供% %所以必须在此处插入模糊关键词
        QueryVo queryVo = new QueryVo();    //创建vo
        queryVo.setUser(user);              //将user传入,提供username值
        List<User> users = mapper.findUserByVo(queryVo);    //执行查询
        for (User u:users) {
            System.out.println(u);
        }
    }




    //创建用户
    @Test
    public void createUser() throws IOException {
        //定义一个user
        User user = new User();
        user.setUsername("mybatis_user02");
        user.setSex("man");
        user.setBirthday(new java.sql.Date(new Date().getTime()));  //当天日期
        user.setAddress("unspecified");

        mapper.createUser(user);

        sqlSession.commit();            //mybatis默认不自动提交，需要自行commit

        System.out.println(user);       //因为使用了selectKey，返回了id值，此时id不再是null
    }


    //更新
    @Test
    public void updateUser() {
        User user = new User();
        user.setUsername("mybatis_user01");
        user.setSex("man");
        user.setBirthday(new java.sql.Date(new Date().getTime()));          //当天日期
        user.setAddress("changed");
        user.setId(12);

        mapper.updateUser(user);

        sqlSession.commit();
    }


    //删除
    @Test
    public void deleteUser() {
        mapper.deleteUser(3);

        sqlSession.commit();
    }
}
