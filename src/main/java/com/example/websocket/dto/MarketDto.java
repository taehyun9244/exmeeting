package com.example.websocket.dto;

import lombok.Data;

@Data
public class MarketDto {
    private String itemName;
    private String itemBody;
    private int itemPrice;
}
