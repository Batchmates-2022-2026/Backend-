package com.HaritMitraBack.mitra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PestTreatmentService {

    @Autowired
    private AIService aiService;

    public String getTreatment(String pest) {
        return aiService.askAI(pest);
    }
}