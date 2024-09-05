package com.estudos.chat.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.estudos.chat.model.dtos.request.UsuarioCadastroRequestDTO;
import com.estudos.chat.model.entity.Usuario;

@Mapper
public interface MapperS {

    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioCadastroRequestDTO dto);
}
