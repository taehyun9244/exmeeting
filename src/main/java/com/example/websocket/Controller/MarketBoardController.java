package com.example.websocket.Controller;

import com.example.websocket.Service.MarketService;
import com.example.websocket.dto.MarketDto;
import com.example.websocket.model.MarketBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MarketBoardController {

    private final MarketService marketService;

    @PostMapping("/markets")
    public MarketBoard creatMarket(@RequestBody MarketDto dto){
        return marketService.creatMarket(dto);
    }
}
