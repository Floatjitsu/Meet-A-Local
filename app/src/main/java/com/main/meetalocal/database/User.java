package com.main.meetalocal.database;

public class User {

    private String firstName, surname, country, homeTown, email, uId;

    public User(String firstName, String surname, String country, String homeTown, String email) {
        this.firstName = firstName;
        this.surname = surname;
        this.country = country;
        this.homeTown = homeTown;
        this.email = email;
    }

    void setuId(String uId) {
        this.uId = uId;
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

    public String getuId() {
        return uId;
    }
}
