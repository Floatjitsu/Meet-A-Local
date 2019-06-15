package com.main.meetalocal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.main.meetalocal.database.room.BucketListCountry;
import com.main.meetalocal.database.room.BucketListCountryDao;
import com.main.meetalocal.database.room.BucketListDatabase;
import com.main.meetalocal.database.room.BucketListRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


    public void insert(BucketListCountry bucketListCountry) {
        bucketListRepository.insert(bucketListCountry);
    }

}
