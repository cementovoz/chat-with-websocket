package com.chat.webapp;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class Chat extends TextWebSocketHandler {

    private List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessionList.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        WebSocketMessage<?> newMessage = new TextMessage(message.getPayload());
        sessionList.stream().filter( it -> !it.equals(session)).forEach(it -> {
            try {
                it.sendMessage(newMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
