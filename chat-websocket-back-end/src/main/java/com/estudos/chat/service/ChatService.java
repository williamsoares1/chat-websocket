package com.estudos.chat.service;

import com.estudos.chat.model.dtos.request.ChatCreateRequestDTO;

public interface ChatService {
    String criarChat(ChatCreateRequestDTO dto);
}
