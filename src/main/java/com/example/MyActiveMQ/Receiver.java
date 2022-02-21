package com.example.MyActiveMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Receiver {

    private List<Email> list = new ArrayList<>();
    private int count = 0;

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        log.info("Received <" + email + ">");

        list.add(email);
        count++;

        if (count == 10) {

            MessageResult messageResult
                    = new MessageResult(email.getCorrelationID(), email.getFirstNum() + email.getSecondNum());
            System.out.println("m: " + messageResult);
            jmsTemplate.convertAndSend("reply", messageResult);
        }
    }


}
