package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.dto.SoilRequest;
import com.HaritMitraBack.mitra.dto.SoilResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoilService {

    public SoilResponse analyze(SoilRequest req) {

        String status = "Good";
        List<String> tips = new ArrayList<>();

        if (req.getPh() < 6) {
            status = "Acidic";
            tips.add("Add lime to increase pH");
        } else if (req.getPh() > 8) {
            status = "Alkaline";
            tips.add("Add gypsum to reduce pH");
        }

        if (req.getNitrogen() < 200)
            tips.add("Add nitrogen-rich fertilizers (Urea)");

        if (req.getPhosphorus() < 15)
            tips.add("Add DAP or SSP");

        if (req.getPotassium() < 150)
            tips.add("Add MOP (Potash)");

        if (req.getOrganic() < 0.5)
            tips.add("Add compost or manure");

        if (tips.isEmpty())
            tips.add("Your soil is healthy!");

        return new SoilResponse(status, tips);
    }
}