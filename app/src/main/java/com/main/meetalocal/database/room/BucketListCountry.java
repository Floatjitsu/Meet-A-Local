package com.main.meetalocal.database.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Representation of a countryName in users personal bucket list
 * Only contains name of the countries
 */
@Entity(tableName = "user_bucket_list")
public class BucketListCountry {

    @PrimaryKey @NonNull
    private String countryName;

    public BucketListCountry(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
