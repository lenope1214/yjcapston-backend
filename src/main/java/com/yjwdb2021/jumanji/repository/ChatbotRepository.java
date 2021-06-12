package com.yjwdb2021.jumanji.repository;

import com.yjwdb2021.jumanji.data.Chatbot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {
    List<Chatbot> findByShop_IdOrderByIdDesc(String shopId);

}