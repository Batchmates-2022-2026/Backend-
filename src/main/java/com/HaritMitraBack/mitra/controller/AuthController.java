package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.dto.LoginRequest;
import com.HaritMitraBack.mitra.dto.SignupRequest;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.service.UserService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://haritbandhu-frontend-git-main-sneha-guptas-projects-df0fd354.vercel.app")
public class AuthController {

    private final UserService userService;
    private final ActivityService activityService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,
                          ActivityService activityService,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.activityService = activityService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ SIGNUP
    @PostMapping("/signup")
    public Object signup(@Valid @RequestBody SignupRequest request) {

        String res = userService.signup(request);

        // 🔥 activity log
        activityService.log(
                request.getEmail(),
                "SIGNUP",
                "New user registered"
        );

        return Map.of("message", res);
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request) {

        String result = userService.login(request.getEmail(), request.getPassword());

        // ❌ LOGIN FAILED
        if (result.startsWith("User") || result.startsWith("Invalid")) {

            activityService.log(
                    request.getEmail(),
                    "LOGIN_FAILED",
                    "Invalid login attempt"
            );

            return Map.of("error", result);
        }

        // ✅ SUCCESS LOGIN
        String jwt = result;
        String email = jwtUtil.extractEmail(jwt);

        // 🔥 activity log
        activityService.log(
                email,
                "LOGIN_SUCCESS",
                "User logged in"
        );

        // 🔥 activity update (VERY IMPORTANT)
        activityService.updateUserActivity(email);

        return Map.of(
                "token", jwt
        );
    }
}
