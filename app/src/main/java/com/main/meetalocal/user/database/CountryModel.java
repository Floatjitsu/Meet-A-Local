package com.main.meetalocal.user.database;

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

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setRoundedFlagPath(String roundedFlagPath) {
        this.roundedFlagPath = roundedFlagPath;
    }
}
