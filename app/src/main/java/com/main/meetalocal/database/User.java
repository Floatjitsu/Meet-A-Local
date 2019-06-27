package com.main.meetalocal.database;

public class User {

    private String firstName, surname, country, homeTown, email;

    public User() {}

    public User(String firstName, String surname, String country, String homeTown, String email) {
        this.firstName = firstName;
        this.surname = surname;
        this.country = country;
        this.homeTown = homeTown;
        this.email = email;
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

    public String getHomeTown() {
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
}
