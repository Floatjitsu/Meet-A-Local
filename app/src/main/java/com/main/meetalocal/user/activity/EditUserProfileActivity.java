package com.main.meetalocal.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.main.meetalocal.R;
import com.main.meetalocal.user.viewmodel.ViewModelUser;

public class EditUserProfileActivity extends AppCompatActivity {

    EditText mFirstName, mSurname, mCountry, mHomeTown, mAbout, mLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        mFirstName = findViewById(R.id.edit_text_edit_first_name);
        mSurname = findViewById(R.id.edit_text_edit_surname);
        mCountry = findViewById(R.id.edit_text_edit_country);
        mHomeTown = findViewById(R.id.edit_text_edit_home_town);
        mAbout = findViewById(R.id.edit_text_edit_about);
        mLanguages = findViewById(R.id.edit_text_edit_languages);

        Toolbar editProfileToolbar = findViewById(R.id.toolbar_edit_user_profile_activity);
        setSupportActionBar(editProfileToolbar);

        setUserObserver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_user_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set the LiveData Observer for users email, first name and surname
     *  After receiving the data the corresponding EditTexts will be assigned with values
     */
    private void setUserObserver() {
        ViewModelUser viewModelUser = ViewModelProviders.of(this).get(ViewModelUser.class);
        LiveData<Task<DocumentSnapshot>> liveData = viewModelUser.getDataSnapshotLiveData();
        liveData.observe(this, new Observer<Task<DocumentSnapshot>>() {
            @Override
            public void onChanged(Task<DocumentSnapshot> documentSnapshotTask) {
                if(documentSnapshotTask.isSuccessful()) {
                    DocumentSnapshot snapshot = documentSnapshotTask.getResult();
                    if(snapshot != null) {
                        mFirstName.setText(snapshot.getString("firstName"));
                        mSurname.setText(snapshot.getString("surname"));
                        mCountry.setText(snapshot.getString("country"));
                        mHomeTown.setText(snapshot.getString("homeTown"));
                        mAbout.setText(snapshot.getString("about"));
                        mLanguages.setText(snapshot.getString("languages"));
                    }
                }
            }
        });
    }
}
