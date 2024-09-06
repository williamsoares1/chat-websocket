package com.estudos.chat.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudos.chat.domain.dtos.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{

}
