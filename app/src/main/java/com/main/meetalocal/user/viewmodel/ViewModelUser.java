package com.main.meetalocal.user.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.main.meetalocal.user.database.Authentication;

public class ViewModelUser extends ViewModel {

    private final String FIRESTORE_PATH_USER = "users";
    private final FirebaseFirestore DATABASE = FirebaseFirestore.getInstance();
    private final FirestoreQueryLiveData LIVE_DATA =
            new FirestoreQueryLiveData(DATABASE.collection(FIRESTORE_PATH_USER)
            .document(new Authentication().getCurrentUserUid()));

    @NonNull
    public LiveData<Task<DocumentSnapshot>> getDataSnapshotLiveData(){
        return LIVE_DATA;
    }


}
