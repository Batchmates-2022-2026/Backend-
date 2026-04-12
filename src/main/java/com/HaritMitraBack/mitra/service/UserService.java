package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.dto.SignupRequest;
import com.HaritMitraBack.mitra.model.User;
import com.HaritMitraBack.mitra.repository.UserRepository;
import com.HaritMitraBack.mitra.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // 🔐 SIGNUP
    public String signup(SignupRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "User already exists";
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        return "Signup successful";
    }
    public String login(String email, String password) {

        var userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) return "User not found";

        User user = userOpt.get();

        if (!encoder.matches(password, user.getPassword())) {
            return "Invalid password";
        }

        // 🔥 TOKEN GENERATE
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    // 🔥 AUTO ADMIN CREATE
    @PostConstruct
    public void createAdmin() {

        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole("ADMIN");

            userRepository.save(admin);
        }
    }
}