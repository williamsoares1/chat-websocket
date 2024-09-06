package com.estudos.chat.service.impl;

import java.time.LocalDate;
import java.util.Set;
import java.util.Optional;
import java.util.HashSet;

import org.apache.tomcat.util.ExceptionUtils;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.ExceptionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.stereotype.Service;

import com.estudos.chat.domain.dtos.entity.Chat;
import com.estudos.chat.domain.dtos.entity.Usuario;
import com.estudos.chat.domain.dtos.request.ChatCreateRequestDTO;
import com.estudos.chat.repository.postgresql.ChatRepository;
import com.estudos.chat.repository.postgresql.UsuarioRepository;
import com.estudos.chat.service.ChatService;
import com.estudos.chat.util.crypt.AESUtil;
import com.estudos.chat.util.mapper.MapperS;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private MapperS mapper = Mappers.getMapper(MapperS.class);

    @Override
    public String criarChat(ChatCreateRequestDTO dto) {
        log.error(dto + "IDDDDD");
        Chat entity = mapper.toEntity(dto);
        Set<Usuario> usuarios = new HashSet<Usuario>();
        
        for (String id : dto.usuariosId()){
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(decryptId(id));

            if(!usuarioOpt.isPresent()) throw new RuntimeException("Usuario não encontrado");

            Usuario usuario = usuarioOpt.get();

            usuarios.add(usuario);
            usuario.getChats().add(entity);
        }

        entity.setUsuarios(usuarios);
        entity.setCreatedAt(LocalDate.now());

        try {
            repository.save(entity);
        } catch (Exception e) {
            throw new RepositoryCreationException(e.getMessage(), getClass());
        }

        return "foi";
    }

    public Long decryptId(String encryptedId) {

        Long decryptedId;

        try {
            decryptedId = AESUtil.decrypt(encryptedId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar o ID do usuário", e);
        }

        return decryptedId;
    }

}
