package com.HaritMitraBack.mitra.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class EngagementService {
    private final List<String> messages = List.of(
            "💬 Let's talk! Ask anything about farming 🌱",
            "📸 Got a pest issue? Upload image & get instant help 🐛",
            "📊 Check today's market prices before selling 💰",
            "📢 New government schemes waiting for you! 📜",
            "🌦️ Let's check today's weather for your crops ☀️🌧️",
            "🚜 Stay updated, stay ahead in farming!",
            "🌱 Smart farming starts with smart decisions 💡",
            "📍 Check updates for your area now!",
            "👋 Hey! Don’t miss important alerts today",
            "🔥 Your farm deserves the best care—let’s start!",
            "🌾 Time to boost your crop productivity!",
            "📈 See what's trending in the market today",
            "🤖 Chat with AI & solve your farming doubts instantly",
            "🌧️ Rain or heat? Stay prepared with weather alerts!",
            "✨ Explore new opportunities for better yield"
    );

    public String getRandomMessage() {
        Random random = new Random();
        return messages.get(random.nextInt(messages.size()));
    }
}