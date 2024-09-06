package com.estudos.chat.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.estudos.domain_ms.dtos.request.ChatCreateRequestDTO;
import com.estudos.domain_ms.dtos.request.MessageDTO;
import com.estudos.domain_ms.dtos.request.UsuarioRegisterRequestDTO;
import com.estudos.domain_ms.entity.Chat;
import com.estudos.domain_ms.entity.Usuario;
import com.estudos.domain_ms.model.Message;

@Mapper
public interface MapperS {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chats", ignore = true)
    Usuario toEntity(UsuarioRegisterRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Chat toEntity(ChatCreateRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Message toEntity(MessageDTO dto);
}