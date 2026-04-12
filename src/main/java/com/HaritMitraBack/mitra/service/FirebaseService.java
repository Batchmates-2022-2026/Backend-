package com.HaritMitraBack.mitra.service;

import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    public void sendNotification(String token, String title, String message) {

        // 🔥 TEMP (for testing)
        System.out.println("📲 Notification Sent:");
        System.out.println("Token: " + token);
        System.out.println("Title: " + title);
        System.out.println("Message: " + message);

        // ⚡ Later we will connect real Firebase
    }
}