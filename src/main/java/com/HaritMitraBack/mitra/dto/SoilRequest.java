package com.HaritMitraBack.mitra.dto;

public class SoilRequest {

    private double nitrogen;
    private double phosphorus;
    private double potassium;
    private double ph;
    private double organic;

    // getters setters
    public double getNitrogen() { return nitrogen; }
    public void setNitrogen(double nitrogen) { this.nitrogen = nitrogen; }

    public double getPhosphorus() { return phosphorus; }
    public void setPhosphorus(double phosphorus) { this.phosphorus = phosphorus; }

    public double getPotassium() { return potassium; }
    public void setPotassium(double potassium) { this.potassium = potassium; }

    public double getPh() { return ph; }
    public void setPh(double ph) { this.ph = ph; }

    public double getOrganic() { return organic; }
    public void setOrganic(double organic) { this.organic = organic; }
}