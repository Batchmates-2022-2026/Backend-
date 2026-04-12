package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.dto.SchemeDTO;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.service.SchemeService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schemes")
@CrossOrigin
public class SchemeController {

    private ActivityService activityService;

    private JwtUtil jwtUtil;

    private final SchemeService service;

    public SchemeController(SchemeService service, ActivityService activityService, JwtUtil jwtUtil) {
        this.service = service;
        this.activityService = activityService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<SchemeDTO> getSchemes(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String category) {

        String jwt = token.replace("Bearer", "").trim();
        String email = jwtUtil.extractEmail(jwt);
activityService.updateUserActivity(email);
        List<SchemeDTO> result = service.getSchemes(category);

        // 🔥 ACTIVITY LOG
        activityService.log(
                email,
                "VIEW_SCHEMES",
                category != null ? category : "All schemes"
        );

        return result;
    }
}