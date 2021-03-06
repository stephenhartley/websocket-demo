package com.hartley.websockets.server;

import java.io.IOException;
import java.util.logging.Logger;
 
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
 
@ServerEndpoint(value = "/game")
public class WordgameServerEndpoint {
 
    private Logger logger = Logger.getLogger(this.getClass().getName());
 
    @OnOpen
    public void onOpen(Session session) {
        String version = System.getProperty("java.version");
        logger.info("Java version from System.getProperty(\"java.version\") is " + version);
        logger.info("Connected ... " + session.getId());
    }
 
    @OnMessage
    public String onMessage(Session session, String message) {
        logger.info("in onMessage " + session.getId());
        switch (message) {
        case "quit":
            try {
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game ended"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            break;
        }
        
        return message;
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }
}