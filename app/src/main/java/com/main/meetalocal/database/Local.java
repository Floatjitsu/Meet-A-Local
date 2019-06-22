package com.main.meetalocal.database;

/**
 * This class represents a local in the database
 */
public class Local extends User {

    private String introduction;
    private boolean providesAccommodation, providesAccompany, providesGuidedTours;

    public Local(String firstName, String surname, String country, String homeTown, String email) {
        super(firstName, surname, country, homeTown, email);
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isProvidesAccommodation() {
        return providesAccommodation;
    }

    public void setProvidesAccommodation(boolean providesAccommodation) {
        this.providesAccommodation = providesAccommodation;
    }

    public boolean isProvidesAccompany() {
        return providesAccompany;
    }

    public void setProvidesAccompany(boolean providesAccompany) {
        this.providesAccompany = providesAccompany;
    }

    public boolean isProvidesGuidedTours() {
        return providesGuidedTours;
    }

    public void setProvidesGuidedTours(boolean providesGuidedTours) {
        this.providesGuidedTours = providesGuidedTours;
    }
}
