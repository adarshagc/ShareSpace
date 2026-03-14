package com.adarsha.sharespace.websocket;

import com.adarsha.sharespace.dto.TextMessage;
import com.adarsha.sharespace.service.WorkspaceService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WorkspaceWebSocketController {

    private final WorkspaceService workspaceService;

    public WorkspaceWebSocketController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @MessageMapping("/workspace/{code}")
    @SendTo("/topic/workspace/{code}")
    public TextMessage updateText(
            @DestinationVariable String code,
            TextMessage message
    ) {

        workspaceService.updateText(code, message.getTextContent());

        return message;
    }
}