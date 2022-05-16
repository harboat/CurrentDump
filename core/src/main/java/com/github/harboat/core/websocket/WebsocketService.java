package com.github.harboat.core.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;
    @Value("${broker.destination-prefix}")
    private String destination;

    @Async("websocketServiceThreads")
    public void notifyFrontEnd(String username, Event<?> event) {
        messagingTemplate.convertAndSendToUser(username, destination, event);
    }
}
