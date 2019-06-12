package com.main.meetalocal.database;

public class CountryModel {

    private String countryName, roundedFlagPath;

    CountryModel() {}

    public CountryModel(String countryName, String roundedFlagPath) {
        this.countryName = countryName;
        this.roundedFlagPath = roundedFlagPath;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRoundedFlagPath() {
        return roundedFlagPath;
    }
}
