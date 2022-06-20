package com.example.websocket.Service;

import com.example.websocket.dto.MarketDto;
import com.example.websocket.model.MarketBoard;
import com.example.websocket.repository.MarketBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketBoardRepository marketBoardRepository;
    @Transactional
    public MarketBoard creatMarket(MarketDto dto) {
        String itemName = dto.getItemName();
        String itemBody = dto.getItemBody();
        Integer itemPrice = dto.getItemPrice();
        MarketBoard marketBoard = new MarketBoard(itemName, itemBody, itemPrice);
        return marketBoardRepository.save(marketBoard);
    }
}
