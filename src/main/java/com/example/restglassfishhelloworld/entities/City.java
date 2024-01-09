package com.example.restglassfishhelloworld.entities;

public class City {

    private int id;
    private String name;
    private String countryCode;
    private String district;

    public City() {
    }

    public City(int id, String name, String countryCode, String district) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
