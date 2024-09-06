package com.estudos.chat.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.estudos.chat.domain.document.ChatDocument;

@Repository
public interface ChatMongoRepository extends MongoRepository<ChatDocument, Long>{

}
