package com.example.websocket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MarketBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String itemName;
    private String itemBody;
    private int itemPrice;

    @OneToOne
    @JoinColumn(name = "marketBoard")
    private ChatRoom chatRoom;

    public MarketBoard(String itemName, String itemBody, Integer itemPrice) {
        this.itemName = itemName;
        this.itemBody = itemBody;
        this.itemPrice = itemPrice;
    }
}
