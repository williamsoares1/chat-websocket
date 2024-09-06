package com.estudos.chat.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.estudos.chat.domain.dtos.entity.Chat;
import com.estudos.chat.domain.dtos.entity.Usuario;
import com.estudos.chat.domain.dtos.request.ChatCreateRequestDTO;
import com.estudos.chat.domain.dtos.request.MessageDTO;
import com.estudos.chat.domain.dtos.request.UsuarioRegisterRequestDTO;
import com.estudos.chat.model.Message;

@Mapper
public interface MapperS {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chats", ignore = true)
    Usuario toEntity(UsuarioRegisterRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Chat toEntity(ChatCreateRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Message toEntity(MessageDTO dto);
}
