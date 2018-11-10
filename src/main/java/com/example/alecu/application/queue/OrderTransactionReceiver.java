package com.example.alecu.application.queue;

import com.example.alecu.application.controller.BikeController;
import com.example.alecu.application.models.Bike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderTransactionReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTransactionReceiver.class);

    @JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
    public void receiveMessage(Bike transaction) {
        LOGGER.info("Received <" + transaction + ">");
    }
}
