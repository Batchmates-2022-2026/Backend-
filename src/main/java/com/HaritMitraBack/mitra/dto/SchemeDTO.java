package com.HaritMitraBack.mitra.dto;

public class SchemeDTO {

    private String name;
    private String category;
    private String status;
    private String applyLink;

    public SchemeDTO(String name, String category, String status, String applyLink) {
        this.name = name;
        this.category = category;
        this.status = status;
        this.applyLink = applyLink;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public String getApplyLink() { return applyLink; }
}