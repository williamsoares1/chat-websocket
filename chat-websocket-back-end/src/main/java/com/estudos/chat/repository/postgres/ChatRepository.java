package com.estudos.chat.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudos.chat.model.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{

}
