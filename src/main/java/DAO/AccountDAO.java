package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account loginAccount(Account account) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String username = account.getUsername();
        String password = account.getPassword();

        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        //ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()){
            int generatedId = resultSet.getInt(1);
            return new Account(generatedId, username, password);
        }
        
        return null; 

    }
    
    public Account insertAccount(Account account) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String username = account.getUsername();
        String password = account.getPassword();
        if (!username.isBlank() && (password.length() > 3)) {
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            //ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()){
                sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int generatedId = resultSet.getInt(1);
                return new Account(generatedId, username, password);
            }
        }
        return null;
    }

}
