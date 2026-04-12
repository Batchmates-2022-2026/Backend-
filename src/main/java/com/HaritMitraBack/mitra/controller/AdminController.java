package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.model.User;
import com.HaritMitraBack.mitra.repository.UserRepository;
import com.HaritMitraBack.mitra.repository.ActivityRepository;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;

    public AdminController(UserRepository userRepository,
                           JwtUtil jwtUtil,
                           ActivityRepository activityRepository,
                           ActivityService activityService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.activityRepository = activityRepository;
        this.activityService = activityService;
    }

    // 🔐 COMMON METHOD (avoid repetition)
    private Claims validateAdmin(String token) {
        String jwt = token.replace("Bearer ", "").trim();
        Claims claims = jwtUtil.validateToken(jwt);

        String role = (String) claims.get("role");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied");
        }

        return claims;
    }

    // 👥 GET ALL USERS
    @GetMapping("/users")
    public Object getUsers(@RequestHeader("Authorization") String token) {

        Claims claims = validateAdmin(token);

        // 🔥 activity log (admin active)
        String email = claims.getSubject();
        activityService.updateUserActivity(email);

        return userRepository.findAll();
    }

    // 📊 STATS
    @GetMapping("/stats")
    public Object getStats(@RequestHeader("Authorization") String token) {

        Claims claims = validateAdmin(token);

        // 🔥 activity
        String email = claims.getSubject();
        activityService.updateUserActivity(email);

        return Map.of(
                "totalUsers", userRepository.count()
        );
    }

    // ❌ DELETE USER
    @DeleteMapping("/user/{id}")
    public Object deleteUser(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {

        Claims claims = validateAdmin(token);

        String email = claims.getSubject();

        // 🔥 activity
        activityService.updateUserActivity(email);

        userRepository.deleteById(id);

        return Map.of("message", "User deleted");
    }

    // 📜 VIEW ACTIVITY LOGS
    @GetMapping("/activity")
    public Object getActivity(@RequestHeader("Authorization") String token) {

        Claims claims = validateAdmin(token);

        String email = claims.getSubject();

        // 🔥 activity
        activityService.updateUserActivity(email);

        return activityRepository.findAll();
    }
}