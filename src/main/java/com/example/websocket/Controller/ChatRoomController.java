package com.example.websocket.Controller;


import com.example.websocket.Service.ChatRoomService;
import com.example.websocket.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService roomService;

    @PostMapping("/{marketId}/room")
    public void creatRoom(@PathVariable Long marketId, User user){
        roomService.creatRoom(marketId, user);
    }
}
