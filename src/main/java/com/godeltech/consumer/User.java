package com.godeltech.consumer;

import com.godeltech.config.RabbitConfiguration;
import com.godeltech.dto.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class User {
    @RabbitListener(queues = RabbitConfiguration.QUEUE)
    public String consumeMessageFromQueue(OrderStatus orderStatus)
    {
        log.info("Message received from queue : " + orderStatus);
        return "Received on consumer " + orderStatus.toString();
    }
}
