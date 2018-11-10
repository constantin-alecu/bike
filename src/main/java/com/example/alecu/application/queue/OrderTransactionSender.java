package com.example.alecu.application.queue;

import com.example.alecu.application.models.Bike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderTransactionSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTransactionSender.class);

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendTransaction(Bike bike){
        LOGGER.info("Sending a transaction");
        jmsTemplate.convertAndSend(bike);
    }
}
