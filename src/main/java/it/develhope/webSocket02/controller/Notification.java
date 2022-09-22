package it.develhope.webSocket02.controller;

import it.develhope.webSocket02.entities.ClientMessageDTO;
import it.develhope.webSocket02.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Chiedere chiarimenti a Carlo sui metodi che seguono.
//Non stampa i messaggi sull'interfaccia web (localhost:8080)
@Controller
public class Notification {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/broadcast-message")
    public ResponseEntity sendNotificationToClient(@RequestBody MessageDTO messageDTO){
        simpMessagingTemplate.convertAndSend("/topic/broadcast",messageDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
}

    @MessageMapping("/client-message")
    @SendTo("/topic/broadcast")
    public MessageDTO handleMessageFromWebSocket(ClientMessageDTO clientMessageDTO){
        System.out.println("Arrived something on /app/hello -" + clientMessageDTO.getClientAlert() + " " + clientMessageDTO.getClientMessage());
        return new MessageDTO("Message from client: " + clientMessageDTO.getClientMessage(), "- message: " + clientMessageDTO.getClientName(), "allert: " + clientMessageDTO.getClientAlert());

    }

}
