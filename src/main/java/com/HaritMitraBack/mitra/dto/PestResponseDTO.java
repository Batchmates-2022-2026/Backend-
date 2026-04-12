package com.HaritMitraBack.mitra.dto;

public class PestResponseDTO {

    private String pest;
    private double confidence;

    private String message;

    // 🔹 Default Constructor
    public PestResponseDTO() {
    }

    // 🔹 Parameterized Constructor
    public PestResponseDTO(String pest, double confidence,String message) {
        this.pest = pest;
        this.confidence = confidence;

        this.message = message;
    }

    // 🔹 Getters and Setters

    public String getPest() {
        return pest;
    }

    public void setPest(String pest) {
        this.pest = pest;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}