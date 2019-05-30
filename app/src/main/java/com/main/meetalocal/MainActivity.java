package com.main.meetalocal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.main.meetalocal.viewmodel.ViewModelUser;

public class MainActivity extends AppCompatActivity {

    TextView mUserEmail, mUserFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserEmail = findViewById(R.id.test_user_email);
        mUserFirstName = findViewById(R.id.test_user_first_name);
        /*

        ViewModelUser viewModelUser = ViewModelProviders.of(this).get(ViewModelUser.class);
        LiveData<Task<DocumentSnapshot>> liveData = viewModelUser.getdataSnapshotLiveData();
        liveData.observe(this, new Observer<Task<DocumentSnapshot>>() {
            @Override
            public void onChanged(Task<DocumentSnapshot> documentSnapshotTask) {
                if(documentSnapshotTask.isSuccessful()) {
                    DocumentSnapshot snapshot = documentSnapshotTask.getResult();
                    mUserEmail.setText(snapshot.get("email").toString());
                    mUserFirstName.setText(snapshot.get("firstName").toString());
                }
            }
        }); */
    }
}
