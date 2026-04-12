package com.HaritMitraBack.mitra.dto;

public class MarketPriceDTO {

    private String market;
    private String district;
    private String state;
    private String price;

    public MarketPriceDTO(String market, String district, String state, String price) {
        this.market = market;
        this.district = district;
        this.state = state;
        this.price = price;
    }

    public String getMarket() { return market; }
    public String getDistrict() { return district; }
    public String getState() { return state; }
    public String getPrice() { return price; }
}