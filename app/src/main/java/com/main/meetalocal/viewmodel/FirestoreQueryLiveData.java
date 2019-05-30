package com.main.meetalocal.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirestoreQueryLiveData extends LiveData<Task<DocumentSnapshot>> {

    private static final String Log_tag = "FirebaseQueryLiveData";
    private final DocumentReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public FirestoreQueryLiveData(DocumentReference reference) {
        this.reference = reference;
    }

    @Override
    protected void onActive() {
        Log.d(Log_tag, "onActive");
        reference.get().addOnCompleteListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(Log_tag, "onInactive");
        reference.get().addOnCompleteListener(listener);
    }

    private class MyValueEventListener implements OnCompleteListener<DocumentSnapshot> {

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            setValue(task);
        }
    }
}
