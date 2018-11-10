package com.example.alecu.application.queue;

import com.example.alecu.application.models.Bike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class OrderTransactionSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTransactionSender.class);

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendTransaction(final Bike bike){
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                ObjectMessage message = session.createObjectMessage(bike);
                message.setJMSType("bike");
                LOGGER.info("Sending message: " + message);

                return message;
            }
        };
        jmsTemplate.send(messageCreator);
    }
}
