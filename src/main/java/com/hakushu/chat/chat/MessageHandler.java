package com.hakushu.chat.chat;

import com.hakushu.chat.chat.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MessageHandler {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public void handleMessage(String message, Session session) throws IOException, EncodeException {
        if (!sessions.containsKey(session.getId())) {
            sessions.put(session.getId(), session);
        }

        log.info("handle message {} from session {}", message, session.getId());

        sendMessage(session);
    }

    public void sendMessage(Session session) throws IOException, EncodeException {
        Message response = new Message();
        response.setContent("I know " + session.getId());
        session.getBasicRemote().sendObject(response);
    }

    public static void clear() {
        sessions.clear();
    }
}
