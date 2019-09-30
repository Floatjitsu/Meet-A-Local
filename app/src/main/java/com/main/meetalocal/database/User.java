package com.main.meetalocal.database;

public class User {

    private String firstName, surname, country, homeTown, email, about, languages, photoUri;

    public User() {}

    public User(String firstName, String surname, String country, String homeTown, String about, String languages) {
        this.firstName = firstName;
        this.surname = surname;
        this.country = country;
        this.homeTown = homeTown;
        this.about = about;
        this.languages = languages;
    }

    public User(String firstName, String surname, String country, String homeTown, String email) {
        this.firstName = firstName;
        this.surname = surname;
        this.country = country;
        this.homeTown = homeTown;
        this.email = email;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getCountry() {
        return country;
    }

    String getHomeTown() {
        return homeTown;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    String getAbout() {
        return about;
    }

    String getLanguages() {
        return languages;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
