package com.web_service.springchat;

import com.web_service.springchat.model.messageDB.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputMessage {
    public OutputMessage(int id_sender, String message, String time) {
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getId_sender(), message.getMessage(), time);
    }
}