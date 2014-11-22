package com.hartley.websockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
 
@ClientEndpoint
public class WordgameClientEndpoint {
 
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    private static CountDownLatch latch;
    
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
        latch.countDown();
    }
 
    public static void main(String[] args) {
        
        
        
        latch = new CountDownLatch(1);
 
        ClientManager client = ClientManager.createClient();
        try {
            
            
            try {
                Session session = client.connectToServer(WordgameClientEndpoint.class, new URI("ws://localhost:8025/websockets/game"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            latch.await();
 
        } catch (DeploymentException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        
        
       
    }
 
    @OnOpen
    public void onOpen(Session session) {
        String version = System.getProperty("java.version");
        logger.info("Java version from System.getProperty(\"java.version\") is " + version);
        logger.info("Connected from the client... " + session.getId());
        try {
            session.getBasicRemote().sendText("start");
            logger.info("Completed send text OK");
        } catch (IOException e) {
            logger.info("Exception was " + e);
            throw new RuntimeException(e);
        }
    }
 
    @OnMessage
    public String onMessage(Session session, String message) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            logger.info("Received ...." + message);
            String userInput = bufferRead.readLine();
            // because this is annotated with @OnMessage a message is sent by the return statement below...
            return userInput;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
 
 
}
