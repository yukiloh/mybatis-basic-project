package dao;

import account.Account;
import account.AccountExtend;
import account.User;

import java.util.List;

public interface IAccountDao {
    List<Account> findAllInAccount();
    List<AccountExtend> findAllInDoubleTable();
    List<User> findAllInUser();
    List<User> findUserById();
}
