package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.model.Crop;
import com.HaritMitraBack.mitra.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {

    private final CropRepository repo;

    public CropService(CropRepository repo) {
        this.repo = repo;
    }

    public List<Crop> getAll() {
        return repo.findAll();
    }

    public List<Crop> filter(String season, String search) {

        if (season.equals("All") && search.isEmpty()) {
            return repo.findAll();
        }

        if (!search.isEmpty()) {
            return repo.findByNameContainingIgnoreCase(search);
        }

        return repo.findBySeason(season);
    }
}