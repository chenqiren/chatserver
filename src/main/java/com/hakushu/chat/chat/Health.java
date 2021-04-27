package com.hakushu.chat.chat;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Slf4j
public class Health {

    @Autowired
    MessageHandler messageHandler;

    @GetMapping(value = "/check")
    public String check() {
        log.info("check health");
        return "I am Healthy";
    }

    public static Logger getLog() {
        return log;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}
