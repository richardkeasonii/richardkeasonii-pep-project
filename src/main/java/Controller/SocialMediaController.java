package Controller;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postUserHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByUserIdHandler);

        return app;
    }

    /**
     * This is a handler for the Account posting endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        try {
            Account addedAccount = accountService.insertAccount(account);
            if (addedAccount != null) {
                //context.json(mapper.writeValueAsString(addedAccount));
                context.json(addedAccount);
                context.status(200);
            } else {
                context.status(400);
            }
        } catch (SQLException e) {
            context.status(500);
        }
        
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        try {
            Account addedAccount = accountService.loginAccount(account);
            if (addedAccount != null) {
                //context.json(mapper.writeValueAsString(addedAccount));
                context.json(addedAccount);
                context.status(200);
            } else {
                context.status(401);
            }
        } catch (SQLException e) {
            context.status(500);
        }
        
    }


    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        try {
            Message addedMessage = messageService.insertMessage(message);
        if (addedMessage != null) {
            //context.json(mapper.writeValueAsString(addedMessage));
            context.json(addedMessage);
            context.status(200);
        } else {
            context.status(400);
        }
        } catch (SQLException e) {
            context.status(500);
        }
}

    private void getMessageHandler(Context context) throws JsonProcessingException {
        try {
            List<Message> messageList = messageService.getAllMessages();
            context.json(messageList);
            context.status(200);  
        } catch (SQLException e) {
            context.status(500);
        }
    }

    private void getMessageByIdHandler(Context context) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();
        //Message message = mapper.readValue(context.body(), Message.class);
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        try {
            Message foundMessage = messageService.getMessageById(message_id);
        if (foundMessage != null) {
            //context.json(mapper.writeValueAsString(foundMessage));
            context.json(foundMessage);
        }
        context.status(200);
        } catch (SQLException e) {
            context.status(500);
        }
    }

    private void deleteMessageByIdHandler(Context context) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();
        //Message message = mapper.readValue(context.body(), Message.class);
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        try {
            Message foundMessage = messageService.deleteMessageById(message_id);
        if (foundMessage != null) {
            //context.json(mapper.writeValueAsString(foundMessage));
            context.json(foundMessage);
        }
        context.status(200);
        } catch (SQLException e) {
            context.status(500);
        }
    }

    private void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        try {
            Message foundMessage = messageService.updateMessageById(message.getMessage_text(), message_id);
        if (foundMessage != null) {
            //context.json(mapper.writeValueAsString(foundMessage));
            context.json(foundMessage);
            context.status(200);
        } else {
            context.status(400);
        }
        } catch (SQLException e) {
            context.status(500);
        }
    }
    
    private void getMessageByUserIdHandler(Context context) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();
        int account_id = context.pathParamAsClass("account_id", Integer.class).get();
        try {
            List<Message> messageList = messageService.getAllMessagesByUserId(account_id);
            context.json(messageList);
            context.status(200);  
        } catch (SQLException e) {
            context.status(500);
        }
    }


}