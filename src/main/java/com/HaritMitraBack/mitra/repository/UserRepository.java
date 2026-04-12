package com.HaritMitraBack.mitra.repository;

import com.HaritMitraBack.mitra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByWeatherAlertEnabledTrue();
}