package com.main.meetalocal.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewModelUser extends ViewModel {

    private static final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private final FirestoreQueryLiveData liveData = new FirestoreQueryLiveData(database.collection("users")
            .document("K9KLDP7FvXRBJUGyPJAH"));

    @NonNull
    public LiveData<Task<DocumentSnapshot>> getdataSnapshotLiveData(){
        return liveData;
    }
}
