package com.example.alecu.application.queue;

import com.example.alecu.application.models.Bike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

@Component
public class OrderTransactionReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTransactionReceiver.class);

    @JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
    public void receiveMessage(ObjectMessage transaction, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) throws JMSException {

        if("bike".equals(jmsMessageHeaderAccessor.getType())) {
            Bike bike = (Bike) transaction.getObject();

            LOGGER.info("Received <" + bike + ">");
        }else{
            LOGGER.info("Received unnecessary message!!");
        }
    }
}
