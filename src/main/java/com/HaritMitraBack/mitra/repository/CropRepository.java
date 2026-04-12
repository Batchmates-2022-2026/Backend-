package com.HaritMitraBack.mitra.repository;

import com.HaritMitraBack.mitra.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, String> {

    List<Crop> findBySeason(String season);

    List<Crop> findByNameContainingIgnoreCase(String name);
}