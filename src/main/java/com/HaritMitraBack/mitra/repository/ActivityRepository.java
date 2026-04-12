package com.HaritMitraBack.mitra.repository;

import com.HaritMitraBack.mitra.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityLog, Long> {
}