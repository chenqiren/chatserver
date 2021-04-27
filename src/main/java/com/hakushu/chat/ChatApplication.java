package com.hakushu.chat;

import com.hakushu.chat.chat.ChatEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ChatApplication.class, args);
        ChatEndpoint.setApplicationContext(configurableApplicationContext);
    }

}
