package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.service.AIService;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {

    private final AIService aiService;
    private final ActivityService activityService;
    private final JwtUtil jwtUtil;

    public ChatController(AIService aiService,
                          ActivityService activityService,
                          JwtUtil jwtUtil) {
        this.aiService = aiService;
        this.activityService = activityService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Object chat(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        try {
            // 🔐 TOKEN → EMAIL
            String jwt = token.replace("Bearer ", "").trim();
            String email = jwtUtil.extractEmail(jwt);

            String message = body.get("message");

            // ❌ validation
            if (message == null || message.trim().isEmpty()) {
                return Map.of("error", "Message cannot be empty");
            }

            // 🔥 ACTIVITY UPDATE (user active)
            activityService.updateUserActivity(email);

            // 🤖 AI RESPONSE
            String aiResponse = aiService.askChat(message);

            // 🔥 ACTIVITY LOG
            activityService.log(
                    email,
                    "CHAT",
                    "User asked: " + message
            );

            return Map.of(
                    "response", aiResponse
            );

        } catch (Exception e) {
            return Map.of("error", "Chat failed");
        }
    }
}