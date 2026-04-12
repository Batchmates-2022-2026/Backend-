package com.HaritMitraBack.mitra.controller;

import com.HaritMitraBack.mitra.model.User;
import com.HaritMitraBack.mitra.repository.UserRepository;
import com.HaritMitraBack.mitra.service.ActivityService;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import com.HaritMitraBack.mitra.utils.OtpUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ActivityService activityService;

    public UserController(UserRepository userRepository,
                          JwtUtil jwtUtil,
                          ActivityService activityService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.activityService = activityService;
    }

    // ✅ Save FCM Token
    @PostMapping("/save-fcm-token")
    public Object saveFcmToken(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        try {
            String jwt = token.replace("Bearer ", "").trim();
            String email = jwtUtil.extractEmail(jwt);

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            user.setFcmToken(body.get("fcmToken"));
            userRepository.save(user);

            activityService.log(email, "SAVE_FCM_TOKEN", "Token saved");

            return Map.of("message", "FCM token saved");

        } catch (Exception e) {
            return Map.of("error", "Failed to save token");
        }
    }

    // ✅ Save Location
    @PostMapping("/location")
    public Object saveLocation(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        try {
            String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            user.setCity(body.get("city"));
            user.setWeatherAlertEnabled(true);

            userRepository.save(user);

            activityService.log(email, "SET_LOCATION", body.get("city"));

            return Map.of("message", "Location saved");

        } catch (Exception e) {
            return Map.of("error", "Failed to save location");
        }
    }

    // ✅ Disable Alerts
    @PostMapping("/disable-alerts")
    public Object disableAlerts(@RequestHeader("Authorization") String token) {

        try {
            String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            user.setWeatherAlertEnabled(false);
            userRepository.save(user);

            activityService.log(email, "DISABLE_ALERTS", "Weather alerts disabled");

            return Map.of("message", "Alerts disabled");

        } catch (Exception e) {
            return Map.of("error", "Failed to disable alerts");
        }
    }

    // ✅ Weather Preferences
    @PostMapping("/weather-preferences")
    public Object updateWeatherPrefs(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> body) {

        try {
            String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            user.setRainAlert((Boolean) body.get("rainAlert"));
            user.setStormAlert((Boolean) body.get("stormAlert"));
            user.setHeatAlert((Boolean) body.get("heatAlert"));
            user.setMonsoonAlert((Boolean) body.get("monsoonAlert"));
            user.setCity((String) body.get("city"));

            userRepository.save(user);

            activityService.log(email, "UPDATE_WEATHER_PREFS", "Preferences updated");

            return Map.of("message", "Preferences saved");

        } catch (Exception e) {
            return Map.of("error", "Failed to update preferences");
        }
    }

    // ✅ Get Profile
    @GetMapping("/profile")
    public Object getProfile(@RequestHeader("Authorization") String token) {

        try {
            String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            activityService.log(email, "VIEW_PROFILE", "Viewed profile");

            return Map.of(
                    "email", user.getEmail(),
                    "username", user.getUsername(),
                    "phone", user.getPhone(),
                    "city", user.getCity(),
                    "emailVerified", user.getEmailVerified(),
                    "phoneVerified", user.getPhoneVerified()
            );

        } catch (Exception e) {
            return Map.of("error", "Failed to fetch profile");
        }
    }

    // ✅ Update Profile
    @PostMapping("/profile")
    public Object updateProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        try {
            String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            user.setUsername(body.get("username"));
            user.setPhone(body.get("phone"));
            user.setCity(body.get("city"));

            userRepository.save(user);

            activityService.log(email, "UPDATE_PROFILE", "Profile updated");

            return Map.of("message", "Profile updated");

        } catch (Exception e) {
            return Map.of("error", "Failed to update profile");
        }
    }

    // 🔐 Change Password
    @PostMapping("/change-password")
    public Object changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        try {
            String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            if (!user.getPassword().equals(body.get("oldPassword"))) {
                return Map.of("error", "Old password incorrect");
            }

            user.setPassword(body.get("newPassword"));
            userRepository.save(user);

            activityService.log(email, "CHANGE_PASSWORD", "Password updated");

            return Map.of("message", "Password updated");

        } catch (Exception e) {
            return Map.of("error", "Failed to change password");
        }
    }

    // 📧 Change Email
    @PostMapping("/change-email")
    public Object changeEmail(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        try {
            String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            user.setEmail(body.get("newEmail"));
            userRepository.save(user);

            activityService.log(email, "CHANGE_EMAIL", "Email updated");

            return Map.of("message", "Email updated");

        } catch (Exception e) {
            return Map.of("error", "Failed to update email");
        }
    }

    // 👤 Current User
    @GetMapping("/me")
    public Object getUser(@RequestHeader("Authorization") String token) {

        try {
            String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

            activityService.updateUserActivity(email);

            User user = userRepository.findByEmail(email).orElseThrow();

            return Map.of(
                    "email", user.getEmail(),
                    "city", user.getCity(),
                    "alertsEnabled", user.isWeatherAlertEnabled()
            );

        } catch (Exception e) {
            return Map.of("error", "User not found");
        }
    }

    // 📧 Send Email OTP
    @PostMapping("/send-email-otp")
    public Object sendEmailOtp(@RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

        User user = userRepository.findByEmail(email).orElseThrow();

        String otp = OtpUtil.generateOtp();
        user.setEmailOtp(otp);
        userRepository.save(user);

        activityService.log(email, "SEND_EMAIL_OTP", "OTP sent");

        return Map.of("message", "OTP sent to email");
    }

    // 📧 Verify Email OTP
    @PostMapping("/verify-email-otp")
    public Object verifyEmailOtp(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

        User user = userRepository.findByEmail(email).orElseThrow();

        if (!body.get("otp").equals(user.getEmailOtp())) {
            return Map.of("error", "Invalid OTP");
        }

        user.setEmailVerified(true);
        user.setEmailOtp(null);
        userRepository.save(user);

        activityService.log(email, "VERIFY_EMAIL", "Email verified");

        return Map.of("message", "Email verified");
    }

    // 📱 Send Phone OTP
    @PostMapping("/send-phone-otp")
    public Object sendPhoneOtp(@RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

        User user = userRepository.findByEmail(email).orElseThrow();

        String otp = OtpUtil.generateOtp();
        user.setPhoneOtp(otp);
        userRepository.save(user);

        activityService.log(email, "SEND_PHONE_OTP", "OTP sent");

        return Map.of("message", "OTP sent to phone");
    }

    // 📱 Verify Phone OTP
    @PostMapping("/verify-phone-otp")
    public Object verifyPhoneOtp(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", "").trim());

        User user = userRepository.findByEmail(email).orElseThrow();

        if (!body.get("otp").equals(user.getPhoneOtp())) {
            return Map.of("error", "Invalid OTP");
        }

        user.setPhoneVerified(true);
        user.setPhoneOtp(null);
        userRepository.save(user);

        activityService.log(email, "VERIFY_PHONE", "Phone verified");

        return Map.of("message", "Phone verified");
    }
}