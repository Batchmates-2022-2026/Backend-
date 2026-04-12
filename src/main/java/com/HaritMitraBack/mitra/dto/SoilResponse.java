package com.HaritMitraBack.mitra.dto;

import java.util.List;

public class SoilResponse {

    private String status;
    private List<String> tips;

    public SoilResponse(String status, List<String> tips) {
        this.status = status;
        this.tips = tips;
    }

    public String getStatus() { return status; }
    public List<String> getTips() { return tips; }
}