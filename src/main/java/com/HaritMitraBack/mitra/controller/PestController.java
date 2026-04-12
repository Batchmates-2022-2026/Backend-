package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.dto.PestResponseDTO;
import com.HaritMitraBack.mitra.service.PestDetectionService;
import com.HaritMitraBack.mitra.service.PestTreatmentService;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/pest")
@CrossOrigin
public class PestController {

    private final PestDetectionService pestDetectionService;
    private final PestTreatmentService pestTreatmentService;
    private final JwtUtil jwtUtil;
    private final ActivityService activityService;

    public PestController(PestDetectionService pestDetectionService,
                          PestTreatmentService pestTreatmentService,
                          JwtUtil jwtUtil,
                          ActivityService activityService) {
        this.pestDetectionService = pestDetectionService;
        this.pestTreatmentService = pestTreatmentService;
        this.jwtUtil = jwtUtil;
        this.activityService = activityService;
    }

    // 🐛 DETECT PEST
    @PostMapping("/detect")
    public Object detect(
            @RequestHeader("Authorization") String token,
            @RequestParam("file") MultipartFile file) {

        try {
            // ❌ validation
            if (file == null || file.isEmpty()) {
                return Map.of("error", "Image file is required");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image")) {
                return Map.of("error", "Only image files are allowed");
            }

            // 🔐 TOKEN → EMAIL
            String jwt = token.replace("Bearer ", "").trim();
            String email = jwtUtil.extractEmail(jwt);

            // 🔥 activity update
            activityService.updateUserActivity(email);

            // 🐛 AI detection
            PestResponseDTO result = pestDetectionService.detect(file);

            // 🔥 activity log
            activityService.log(
                    email,
                    "PEST_DETECTION",
                    result.getPest() != null ? result.getPest() : "No pest"
            );

            return result;

        } catch (Exception e) {
            return Map.of("error", "Pest detection failed");
        }
    }

    // 🌿 GET TREATMENT
    @PostMapping("/treatment")
    public Object getTreatment(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        try {
            String jwt = token.replace("Bearer ", "").trim();
            String email = jwtUtil.extractEmail(jwt);

            String pest = body.get("pest");

            if (pest == null || pest.isEmpty()) {
                return Map.of("error", "Pest name required");
            }

            // 🔥 activity update
            activityService.updateUserActivity(email);

            String result = pestTreatmentService.getTreatment(pest);

            // 🔥 activity log
            activityService.log(
                    email,
                    "PEST_TREATMENT",
                    "Treatment requested for: " + pest
            );

            return Map.of("treatment", result);

        } catch (Exception e) {
            return Map.of("error", "Failed to fetch treatment");
        }
    }
}