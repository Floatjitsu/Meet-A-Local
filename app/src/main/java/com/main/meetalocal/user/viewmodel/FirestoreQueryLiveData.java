package com.main.meetalocal.user.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirestoreQueryLiveData extends LiveData<Task<DocumentSnapshot>> {

    private final DocumentReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    FirestoreQueryLiveData(DocumentReference reference) {
        this.reference = reference;
    }

    @Override
    protected void onActive() {
        reference.get().addOnCompleteListener(listener);
    }

    @Override
    protected void onInactive() {
        reference.get().addOnCompleteListener(listener);
    }

    private class MyValueEventListener implements OnCompleteListener<DocumentSnapshot> {

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            setValue(task);
        }
    }
}
