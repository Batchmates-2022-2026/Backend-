package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.dto.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(NotificationMessage msg) {
        rabbitTemplate.convertAndSend("notificationQueue", msg);
    }
}