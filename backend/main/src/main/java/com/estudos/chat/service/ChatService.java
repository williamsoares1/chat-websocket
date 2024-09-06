package com.estudos.chat.service;

import com.estudos.domain_ms.dtos.request.ChatCreateRequestDTO;

public interface ChatService {
    String criarChat(ChatCreateRequestDTO dto);
}
