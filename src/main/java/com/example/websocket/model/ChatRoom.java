package com.example.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private Long ownUserId;

    @OneToOne(mappedBy = "chatRoom")
    private MarketBoard marketBoard;

    public ChatRoom(String uuid, User user, MarketBoard marketBoard) {
        this.uuid = uuid;
        this.ownUserId = user.getId();
        this.marketBoard = marketBoard;
    }
}
