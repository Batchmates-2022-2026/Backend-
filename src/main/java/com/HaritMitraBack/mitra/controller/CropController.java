package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.model.Crop;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.service.CropService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crop")
@CrossOrigin("*")
public class CropController {

    private final CropService service;
    private final JwtUtil jwtUtil;
    private final ActivityService activityService;

    public CropController(CropService service,
                          JwtUtil jwtUtil,
                          ActivityService activityService) {
        this.service = service;
        this.jwtUtil = jwtUtil;
        this.activityService = activityService;
    }

    // ✅ Get all crops (NO activity)
    @GetMapping("/all")
    public List<Crop> getAll() {
        return service.getAll();
    }

    // ✅ Filter crops (User action → activity)
    @GetMapping("/filter")
    public List<Crop> filter(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "All") String season,
            @RequestParam(defaultValue = "") String search
    ) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

        // 🔥 activity update
        activityService.updateUserActivity(email);

        // 🔥 optional log (good for analytics)
        activityService.log(
                email,
                "CROP_SEARCH",
                "Searched crops with season: " + season + ", search: " + search
        );

        return service.filter(season, search);
    }
}