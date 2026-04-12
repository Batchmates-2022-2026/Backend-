package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.dto.SoilRequest;
import com.HaritMitraBack.mitra.dto.SoilResponse;
import com.HaritMitraBack.mitra.service.SoilService;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/soil")
@CrossOrigin("*")
public class SoilController {

    private final SoilService soilService;
    private final ActivityService activityService;
    private final JwtUtil jwtUtil;

    public SoilController(SoilService soilService,
                          ActivityService activityService,
                          JwtUtil jwtUtil) {
        this.soilService = soilService;
        this.activityService = activityService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/analyze")
    public Object analyze(
            @RequestHeader("Authorization") String token,
            @RequestBody SoilRequest request) {

        try {
            // 🔐 TOKEN → EMAIL
            String jwt = token.replace("Bearer ", "").trim();
            String email = jwtUtil.extractEmail(jwt);

            // 🔥 activity update
            activityService.updateUserActivity(email);

            // 🧪 Soil analysis
            SoilResponse result = soilService.analyze(request);

            // 🔥 activity log
            activityService.log(
                    email,
                    "SOIL_ANALYSIS",
                    "Soil checked (pH: " + request.getPh() + ")"
            );

            return result;

        } catch (Exception e) {
            return Map.of("error", "Soil analysis failed");
        }
    }
}