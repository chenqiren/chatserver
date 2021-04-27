package com.hakushu.chat.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/chat",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
@Component
@Slf4j
public class ChatEndpoint {

    //This is the key to solving the problem of not being able to inject
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ChatEndpoint.applicationContext = applicationContext;
    }

    private MessageHandler messageHandler;

    @OnOpen
    public void onOpen(Session session) {
        log.info("onOpen {}", session.getId());
        messageHandler = applicationContext.getBean(MessageHandler.class);
    }

    @OnClose
    public void onClose() {
        log.info("onClose");
        messageHandler.clear();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("onMessage {}, sessionId {}", message, session.getId());
        try {
            messageHandler.handleMessage(message, session);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("onError {}", error);
    }
}

