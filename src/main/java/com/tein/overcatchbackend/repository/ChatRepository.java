package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {




}

