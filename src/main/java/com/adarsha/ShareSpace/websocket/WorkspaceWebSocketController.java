package com.adarsha.sharespace.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.adarsha.sharespace.dto.TextMessage;
import com.adarsha.sharespace.service.WorkspaceService;

@Controller
@RequiredArgsConstructor
public class WorkspaceWebSocketController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final WorkspaceService workspaceService;

    @MessageMapping("/workspace.update")
    public void updateText(TextMessage message) {
        workspaceService.updateText(message.getCode(), message.getTextContent());
        messagingTemplate.convertAndSend(
            "/topic/workspace/" + message.getCode(),
            message
        );
    } 
}
