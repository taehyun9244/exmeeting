package com.example.websocket.repository;

import com.example.websocket.model.MarketBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketBoardRepository extends JpaRepository<MarketBoard, Long> {
}
