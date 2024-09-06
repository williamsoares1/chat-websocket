package com.estudos.chat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.domain_ms.dtos.request.ChatCreateRequestDTO;
import com.estudos.chat.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService service;
    
    @PostMapping("/create")
    public String CreateChat(@RequestBody ChatCreateRequestDTO dto) {
        return service.criarChat(dto);
    }
    
}
