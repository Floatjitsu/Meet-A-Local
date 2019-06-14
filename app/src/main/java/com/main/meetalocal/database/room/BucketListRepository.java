package com.main.meetalocal.database.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BucketListRepository {

    private BucketListCountryDao bucketListCountryDao;
    private LiveData<List<BucketListCountry>> bucketList;

    public BucketListRepository(Application application) {
        BucketListDatabase database = BucketListDatabase.getInstance(application);
        bucketListCountryDao = database.bucketListCountryDao();
        bucketList = bucketListCountryDao.getBucketList();
    }

    public LiveData<List<BucketListCountry>> getBucketList() {
        return bucketList;
    }
}
