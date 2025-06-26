package Service;

import Model.Account;

import java.sql.SQLException;

import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account insertAccount(Account account) throws SQLException{
        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account) throws SQLException{
        return accountDAO.loginAccount(account);
    }
}
