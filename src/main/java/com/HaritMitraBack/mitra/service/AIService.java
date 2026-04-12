package com.HaritMitraBack.mitra.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AIService {

    @Value("${groq.api.key}")
    private String API_KEY;
    private static final String URL = "https://api.groq.com/openai/v1/chat/completions";

    public String askAI(String message) {

        try {
            OkHttpClient client = new OkHttpClient();

            JSONObject body = new JSONObject();
            body.put("model", "llama-3.1-8b-instant");

            JSONArray messages = new JSONArray();

            JSONObject system = new JSONObject();
            system.put("role", "system");
            system.put("content", "You are an agriculture expert helping farmers.\n" +
                    "Answer in the same language as the user.\n" +
                    "Give treatment, pesticide and prevention.\n" +
                    "Question: " );

            JSONObject user = new JSONObject();
            user.put("role", "user");
            if(message ==null || message.trim().isEmpty()){
                message = "";
            }
            user.put("content",message);

            messages.put(system);
            messages.put(user);

            body.put("messages", messages);

            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(
                            body.toString(),
                            MediaType.parse("application/json")
                    ))
                    .build();

            Response response = client.newCall(request).execute();
            String res = response.body().string();

            System.out.println("AI RAW: " + res);

            JSONObject json = new JSONObject(res);

            if (json.has("error")) {
                return "AI Error: " + json.getJSONObject("error").getString("message");
            }

            return json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (Exception e) {
            return "AI Error: " + e.getMessage();
        }
    }
    public String askChat(String message) {

        try {
            OkHttpClient client = new OkHttpClient();

            JSONObject body = new JSONObject();
            body.put("model", "llama-3.1-8b-instant");

            JSONArray messages = new JSONArray();

            // 🔥 STRONG SYSTEM PROMPT
            JSONObject system = new JSONObject();
            system.put("role", "system");
            system.put("content",
                    "You are a smart agriculture assistant for Indian farmers inside a farming app.\n\n" +

                            "🌾 You ONLY answer questions related to:\n" +
                            "- Agriculture (crops, pests, irrigation, farming methods)\n" +
                            "- Farming processes (how to do something step-by-step)\n" +
                            "- App-related guidance (features inside this farming app)\n\n" +

                            "🌐 Language Rule:\n" +
                            "- Always reply in the SAME language as the user (Hindi, English, Hinglish or regional)\n\n" +

                            "📋 Answer Format:\n" +
                            "1. Solution / Answer\n" +
                            "2. Steps (if question is about process)\n" +
                            "3. Tips\n" +
                            "4. Precautions\n\n" +

                            "🚫 Restrictions:\n" +
                            "- If question is NOT related to agriculture or this app, reply:\n" +
                            "  'Sorry, main sirf farming aur app se related sawalon ka jawab de sakta hoon.'\n" +
                            "- If user uses abusive or inappropriate language, reply:\n" +
                            "  'Kripya shisht bhasha ka upyog karein. Main madad ke liye yahan hoon.'\n\n" +

                            "✔ Keep answers simple, practical and farmer-friendly."
            );

            JSONObject user = new JSONObject();
            user.put("role", "user");
            if(message ==null || message.trim().isEmpty()){
                message = "Give treatment for common crop pest in same language as the user.";
            }
            user.put("content", message);

            messages.put(system);
            messages.put(user);

            body.put("messages", messages);

            Request request = new Request.Builder()
                    .url("https://api.groq.com/openai/v1/chat/completions")
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(
                            body.toString(),
                            MediaType.parse("application/json")
                    ))
                    .build();

            Response response = client.newCall(request).execute();
            String res = response.body().string();

            JSONObject json = new JSONObject(res);

            if (json.has("error")) {
                return "AI Error: " + json.getJSONObject("error").getString("message");
            }

            return json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (Exception e) {
            return "AI Error: " + e.getMessage();
        }
    }


}