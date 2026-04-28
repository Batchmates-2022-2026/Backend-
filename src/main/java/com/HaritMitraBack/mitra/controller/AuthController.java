package com.HaritMitraBack.mitra.controller;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(origins = "https://haritbandhu.netlify.app/")
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
    @PostMapping("/register")
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
public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    String result = userService.login(request.getEmail(), request.getPassword());

    // ❌ LOGIN FAILED CASES
    if (result.equals("User not found") || result.equals("Invalid password")) {

        // 🔥 activity log
        activityService.log(
                request.getEmail(),
                "LOGIN_FAILED",
                result
        );

        return ResponseEntity.status(401).body(Map.of(
                "error", result
        ));
    }

    // ✅ SUCCESS LOGIN
    String jwt = result;

    String email = null;
    try {
        email = jwtUtil.extractEmail(jwt);
    } catch (Exception e) {
        return ResponseEntity.status(500).body(Map.of(
                "error", "Invalid token generated"
        ));
    }

    // 🔥 activity log
    activityService.log(
            email,
            "LOGIN_SUCCESS",
            "User logged in successfully"
    );

    // 🔥 activity update
    activityService.updateUserActivity(email);

    return ResponseEntity.ok(Map.of(
            "token", jwt
    ));
}
}
