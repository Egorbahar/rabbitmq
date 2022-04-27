package com.godeltech.publisher;

import com.godeltech.config.RabbitConfiguration;
import com.godeltech.dto.Order;
import com.godeltech.dto.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderPublisher {
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "Order placed successfully in " + restaurantName);
        String response = (String) rabbitTemplate.convertSendAndReceive(RabbitConfiguration.EXCHANGE, RabbitConfiguration.ROUTING_KEY, orderStatus);
        log.info("Received on publisher: " + response);
        return "Success";
    }
}
