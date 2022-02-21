package com.example.MyActiveMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class Sender {

    List<Email> list = new ArrayList<>();


    @Autowired
    private JmsTemplate jmsTemplate;


    public void sendMessage(){
        for (int i = 1; i <= 20; i++) {
            Email email = new Email(UUID.randomUUID(), i, i);
            list.add(email);
            jmsTemplate.convertAndSend("mailbox", email);
        }
    }


    @JmsListener(destination = "reply")
    public void receiveReply(@Payload MessageResult result) {
        log.info("reply  {}",result.getResult());

        for (Email email : list) {
            if (email.getCorrelationID().equals(result.getCorrelationId())) {
                if (email.getFirstNum() + email.getSecondNum() != result.getResult()) {
                    log.info("NOT MATCH, ERROR");
                } else {
                    log.info("All good");
                }
            }
        }
    }


}
