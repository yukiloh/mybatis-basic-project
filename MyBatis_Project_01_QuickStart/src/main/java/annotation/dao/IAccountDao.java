package annotation.dao;

import account.AccountForAnno;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/*User下的Account账户的dao接口类，但并不是每个user都有account*/
public interface IAccountDao {

    /*查找account下的所有账户，并映射所对应的user*/
    @Select("SELECT * FROM mybatis_account")
    @Results(id = "accountMapper",value={
            /*id:意为主键,只有一个*/
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "money",column = "money"),
            /*语句意义：使user映射（column） uid，并为uid提供查找方式one（一对一）（many一对多）
            为one调用@One标签，select指定具体方法（全类名），fetchType 指定延迟/立即加载
            通过uid去映射findUserById中的id形成多表查询*/
            @Result(property = "user",column = "uid",one = @One(select = "annotation.dao.IUserDao.findUserById",fetchType = FetchType.EAGER)),
    })
    List<AccountForAnno> findAllWithUser();

    @Select("select * from mybatis_account where id like  #{id}")
    AccountForAnno findAccountById(Integer id);




}
