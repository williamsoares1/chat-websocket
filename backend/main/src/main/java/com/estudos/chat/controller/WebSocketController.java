package com.estudos.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.estudos.chat.service.WebSocketService;
import com.estudos.domain_ms.dtos.request.MessageDTO;
import com.estudos.domain_ms.model.Message;

@Controller
public class WebSocketController {

    @Autowired
    private WebSocketService service;

    @MessageMapping("/message/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    public Message sendMessage(@DestinationVariable String chatId, MessageDTO messageDTO) {
        return service.sendMessage(chatId, messageDTO);
    }

}
