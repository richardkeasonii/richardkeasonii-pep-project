package DAO;


import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    public Message createMessage(Message message) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        int posted_by = message.getPosted_by();
        String message_text = message.getMessage_text();
        long time_posted_epoch = message.getTime_posted_epoch();

        String sql = "SELECT * FROM Account WHERE account_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, posted_by);
        ResultSet resultSet = preparedStatement.executeQuery();
        //ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()){
            if (!message_text.isBlank() && (message_text.length() < 256)){
                sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setInt(1, posted_by);
                preparedStatement.setString(2, message_text);
                preparedStatement.setLong(3, time_posted_epoch);
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int message_id = resultSet.getInt("message_id");
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
            }
        }

        return null;
    }

    public List<Message> getAllMessages() throws SQLException{
        List<Message> messageList = new ArrayList<Message>();
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Message";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = preparedStatement.executeQuery();
        //ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while (resultSet.next()){
            int message_id = resultSet.getInt("message_id");
            int posted_by = resultSet.getInt("posted_by");
            String message_text = resultSet.getString("message_text");
            Long time_posted_epoch = resultSet.getLong("time_posted_epoch");
            messageList.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
        }

        return messageList;
    }

    public Message getMessageById(int message_id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, message_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()){
            int posted_by = resultSet.getInt("posted_by");
            String message_text = resultSet.getString("message_text");
            Long time_posted_epoch = resultSet.getLong("time_posted_epoch");
            return new Message(message_id, posted_by, message_text, time_posted_epoch);
        }

        return null;
    }

    public Message deleteMessageById(int message_id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, message_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()){
            int posted_by = resultSet.getInt("posted_by");
            String message_text = resultSet.getString("message_text");
            Long time_posted_epoch = resultSet.getLong("time_posted_epoch");

            sql = "DELETE FROM Message WHERE message_id = ?";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();
            return new Message(message_id, posted_by, message_text, time_posted_epoch);
        }

        return null;
    }

    public Message updateMessageById(String message_text, int message_id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, message_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()){
            if (!message_text.isBlank() && (message_text.length() < 256)){
                int posted_by = resultSet.getInt("posted_by");
                Long time_posted_epoch = resultSet.getLong("time_posted_epoch");

                sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, message_text);
                preparedStatement.setInt(2, message_id);
                preparedStatement.executeUpdate();
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
          }
        }

        return null;
    }

    public List<Message> getAllMessagesByUserId(int user_id) throws SQLException{
        List<Message> messageList = new ArrayList<Message>();
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Message WHERE posted_by = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, user_id);

        ResultSet resultSet = preparedStatement.executeQuery();
        //ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while (resultSet.next()){
            int message_id = resultSet.getInt("message_id");
            int posted_by = resultSet.getInt("posted_by");
            String message_text = resultSet.getString("message_text");
            Long time_posted_epoch = resultSet.getLong("time_posted_epoch");
            messageList.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
        }

        return messageList;
    }

}
