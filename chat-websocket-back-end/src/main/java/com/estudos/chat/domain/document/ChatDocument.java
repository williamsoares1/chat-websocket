package com.estudos.chat.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.estudos.chat.model.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDocument {
    @Id
    private Long id;
    private int totalMessages;
    private int totalUser;
    private LocalDateTime createdAt;
    private List<String> users;
    private List<Message> messages;
}
