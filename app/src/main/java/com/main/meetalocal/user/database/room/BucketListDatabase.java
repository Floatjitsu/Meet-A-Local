package com.main.meetalocal.user.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = BucketListCountry.class, version = 1, exportSchema = false)
public abstract class BucketListDatabase extends RoomDatabase {

    private static BucketListDatabase instance;

    public abstract BucketListCountryDao bucketListCountryDao();

    static synchronized BucketListDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BucketListDatabase.class, "user_bucket_list")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
