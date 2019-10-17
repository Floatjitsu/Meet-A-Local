package com.main.meetalocal.user.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.main.meetalocal.database.Authentication;

public class BucketListViewModel extends ViewModel {

    private static final DatabaseReference BUCKET_LIST_REF =
            FirebaseDatabase.getInstance().getReference("bucketLists")
                .child(new Authentication().getCurrentUserUid());

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(BUCKET_LIST_REF);

    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}
