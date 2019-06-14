package com.main.meetalocal.database.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = BucketListCountry.class, version = 1)
public abstract class BucketListDatabase extends RoomDatabase {

    private static BucketListDatabase instance;

    public abstract BucketListCountryDao bucketListCountryDao();

    public static synchronized BucketListDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BucketListDatabase.class, "user_bucket_list")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
