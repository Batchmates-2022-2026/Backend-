package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.service.WeatherService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final ActivityService activityService;
    private final JwtUtil jwtUtil;
    private final WeatherService weatherService;

    public WeatherController(ActivityService activityService,
                             JwtUtil jwtUtil,
                             WeatherService weatherService) {
        this.activityService = activityService;
        this.jwtUtil = jwtUtil;
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public Object getWeather(
            @RequestHeader("Authorization") String token,
            @PathVariable String city) {

        try {
            // 🔐 TOKEN FIX (important)
            String jwt = token.replace("Bearer ", "").trim();
            String email = jwtUtil.extractEmail(jwt);

            // 🔥 activity update
            activityService.updateUserActivity(email);

            // 🌦 weather API call
            Map<String, Object> result = weatherService.getWeather(city);

            // 🔥 activity log
            activityService.log(
                    email,
                    "WEATHER_CHECK",
                    "Checked weather for " + city
            );

            return result;

        } catch (Exception e) {
            return Map.of("error", "Failed to fetch weather");
        }
    }
}