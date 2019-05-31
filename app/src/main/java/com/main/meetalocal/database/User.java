package com.main.meetalocal.database;

public class User {

    private String firstName, surname, country, homeTown, email;

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
}
