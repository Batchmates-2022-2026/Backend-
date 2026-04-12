package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.dto.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumer {

    private final FirebaseService firebaseService;

    public RabbitConsumer(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @RabbitListener(queues = "notificationQueue")
    public void receive(NotificationMessage msg) {

        System.out.println("📩 Received: " + msg.getMessage());

        // 👉 yaha real notification jayega
        firebaseService.sendNotification(

                msg.getToken(),
                msg.getMessage(),
                msg.getTitle()

        );
    }
}