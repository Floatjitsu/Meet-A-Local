package com.main.meetalocal.user.database.room;

import android.app.Application;
import android.os.AsyncTask;

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

    public LiveData<Integer> countryCount(String countryName) {
        return bucketListCountryDao.countryCount(countryName);
    }

    public void insert(BucketListCountry bucketListCountry) {
         new InsertBucketListCountryAsyncTask(bucketListCountryDao).execute(bucketListCountry);
    }

    public void delete(BucketListCountry bucketListCountry) {
        new DeleteBucketListCountryAsyncTask(bucketListCountryDao).execute(bucketListCountry);
    }

    private static class InsertBucketListCountryAsyncTask extends AsyncTask<BucketListCountry, Void, Void> {

        private BucketListCountryDao bucketListCountryDao;

        private InsertBucketListCountryAsyncTask(BucketListCountryDao bucketListCountryDao) {
            this.bucketListCountryDao = bucketListCountryDao;
        }

        @Override
        protected Void doInBackground(BucketListCountry... bucketListCountries) {
            bucketListCountryDao.insert(bucketListCountries[0]);
            return null;
        }
    }

    private static class DeleteBucketListCountryAsyncTask extends AsyncTask<BucketListCountry, Void, Void> {

        private BucketListCountryDao bucketListCountryDao;

        private DeleteBucketListCountryAsyncTask(BucketListCountryDao bucketListCountryDao) {
            this.bucketListCountryDao = bucketListCountryDao;
        }

        @Override
        protected Void doInBackground(BucketListCountry... bucketListCountries) {
            bucketListCountryDao.delete(bucketListCountries[0]);
            return null;
        }
    }

}
