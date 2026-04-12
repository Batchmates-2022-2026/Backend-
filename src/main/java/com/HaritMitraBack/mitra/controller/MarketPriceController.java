package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.dto.MarketPriceDTO;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.service.MarketPriceService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market-price")
@CrossOrigin
public class MarketPriceController {

    private final MarketPriceService service;
    private final ActivityService activityService;
    private final JwtUtil jwtUtil;

    public MarketPriceController(MarketPriceService service,
                                 ActivityService activityService,
                                 JwtUtil jwtUtil) {
        this.service = service;
        this.activityService = activityService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{crop}")
    public Object getPrices(
            @RequestHeader("Authorization") String token,
            @PathVariable String crop,
            @RequestParam(required = false) String state) {

        try {
            // 🔐 Extract email
            String jwt = token.replace("Bearer ", "").trim();
            String email = jwtUtil.extractEmail(jwt);

            // 🔥 activity update
            activityService.updateUserActivity(email);

            // 📊 fetch data
            List<MarketPriceDTO> result = service.getPrices(crop, state);

            // 🔥 activity log
            activityService.log(
                    email,
                    "MARKET_PRICE",
                    crop + (state != null ? " - " + state : "")
            );

            return result;

        } catch (Exception e) {
            return List.of(); // safe fallback
        }
    }
}