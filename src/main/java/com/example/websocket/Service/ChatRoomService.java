package com.example.websocket.Service;

import com.example.websocket.model.ChatRoom;
import com.example.websocket.model.MarketBoard;
import com.example.websocket.model.User;
import com.example.websocket.repository.ChatRoomRepository;
import com.example.websocket.repository.MarketBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final MarketBoardRepository marketBoardRepository;
    public final ChatRoomRepository chatRoomRepository;
    @Transactional
    public ChatRoom creatRoom(Long marketId, User user) {
        MarketBoard marketBoard = marketBoardRepository.findById(marketId).orElseThrow(
                ()-> new RuntimeException("존재 하지않음")
        );
        String uuid = UUID.randomUUID().toString();
        ChatRoom chatRoom = new ChatRoom(uuid, user, marketBoard);
        return chatRoomRepository.save(chatRoom);
    }
}
