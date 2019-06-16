package com.main.meetalocal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.main.meetalocal.database.room.BucketListCountry;
import com.main.meetalocal.database.room.BucketListRepository;

import java.util.List;

public class BucketListViewModel extends AndroidViewModel {

    private BucketListRepository bucketListRepository;
    private LiveData<List<BucketListCountry>> bucketList;

    public BucketListViewModel(@NonNull Application application) {
        super(application);
        bucketListRepository = new BucketListRepository(application);
        bucketList = bucketListRepository.getBucketList();
    }

    /**
     * Get the whole bucket list for the current user
     * @return the whole bucket list
     */
    public LiveData<List<BucketListCountry>> getBucketList() {
        return bucketList;
    }

    /**
     * Check if a country already exits in the users bucket List
     * @param countryName the country which should get checked
     * @return a live data object from the bucket list repository
     */
    public LiveData<Integer> countryCount(String countryName) {
        return bucketListRepository.countryCount(countryName);
    }

    /**
     * Insert a new country into the the users bucket list
     * @param bucketListCountry the country which should get inserted
     */
    public void insert(BucketListCountry bucketListCountry) {
        bucketListRepository.insert(bucketListCountry);
    }

    /**
     * Delete a country from the users bucket list
     * @param bucketListCountry the country which should get deleted
     */
    public void delete(BucketListCountry bucketListCountry) {
        bucketListRepository.delete(bucketListCountry);
    }

}
