package Service;

import Model.Message;

import java.sql.SQLException;
import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;
    
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message insertMessage(Message message) throws SQLException{
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() throws SQLException{
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) throws SQLException{
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) throws SQLException{
        return messageDAO.deleteMessageById(message_id);
    }  

    public Message updateMessageById(String message_text, int message_id) throws SQLException{
        return messageDAO.updateMessageById(message_text, message_id);
    }

    public List<Message> getAllMessagesByUserId(int user_id) throws SQLException{
        return messageDAO.getAllMessagesByUserId(user_id);
    }
    

}
