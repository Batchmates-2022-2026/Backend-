package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.model.ActivityLog;
import com.HaritMitraBack.mitra.model.User;
import com.HaritMitraBack.mitra.repository.ActivityRepository;
import com.HaritMitraBack.mitra.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActivityService {
    private final UserRepository userRepository;

    private final ActivityRepository repo;

    public ActivityService(ActivityRepository repo ,  UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    public void updateUserActivity(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setLastActiveTime(System.currentTimeMillis());
        userRepository.save(user);
    }


    @Async
    public void log(String email, String action, String details) {

        System.out.println("LOG THR: " + Thread.currentThread().getName());

        ActivityLog log = new ActivityLog();

        log.setEmail(email);
        log.setAction(action);
        log.setDetails(details);
        log.setTime(new Date());

        repo.save(log);
    }
}