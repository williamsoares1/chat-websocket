package com.estudos.chat.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudos.chat.domain.document.ChatDocument;
import com.estudos.chat.domain.dtos.entity.Usuario;
import com.estudos.chat.domain.dtos.request.MessageDTO;
import com.estudos.chat.model.Message;
import com.estudos.chat.repository.mongodb.ChatMongoRepository;
import com.estudos.chat.repository.postgresql.UsuarioRepository;
import com.estudos.chat.util.crypt.AESUtil;
import com.estudos.chat.util.mapper.MapperS;

@Service
public class WebSocketService {

    @Autowired
    private ChatMongoRepository mongoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private MapperS mapper = Mappers.getMapper(MapperS.class);

    public Message sendMessage(String chatId, MessageDTO messageDTO) {

        Long decryptedChatId = decryptId(chatId);
        Long decryptedUserId = decryptId(messageDTO.userId());

        Optional<ChatDocument> chatOpt = mongoRepository.findById(decryptedChatId);

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(decryptedUserId);

        if(!usuarioOpt.isPresent() || !chatOpt.isPresent()) throw new RuntimeException("Dados não presentes.");

        ChatDocument doc = chatOpt.get();

        Message message = mapper.toEntity(messageDTO);
        message.setCreatedAt(LocalDate.now());
        message.setUserId(decryptedUserId);
        doc.getMessages().add(message);
        doc.setTotalMessages(doc.getTotalMessages() + 1);
        doc.setId(decryptedChatId);
        mongoRepository.save(doc);

        return null;
    };

    public Long decryptId(String encryptedId){
        Long decryptedId;
        try {
            decryptedId = AESUtil.decrypt(encryptedId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao decriptar o ID do chat", e);
        }

        return decryptedId;
    }
}
