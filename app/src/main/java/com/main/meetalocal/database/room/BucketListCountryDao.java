package com.main.meetalocal.database.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BucketListCountryDao {

    @Insert
    void insert(BucketListCountry bucketListCountry);

    @Update
    void update(BucketListCountry bucketListCountry);

    @Delete
    void delete(BucketListCountry bucketListCountry);

    @Query("SELECT * FROM user_bucket_list ORDER BY countryName DESC")
    LiveData<List<BucketListCountry>> getBucketList();

    @Query("SELECT COUNT(countryName) FROM user_bucket_list WHERE countryName = :countryName")
    LiveData<Integer> countryCount(String countryName);
}
