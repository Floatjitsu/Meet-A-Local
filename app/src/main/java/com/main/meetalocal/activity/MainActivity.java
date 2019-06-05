package com.main.meetalocal.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.main.meetalocal.R;
import com.main.meetalocal.viewmodel.ViewModelUser;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    TextView mUserEmail, mUserFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout_main_activity);

        mUserEmail = findViewById(R.id.test_user_email);
        mUserFirstName = findViewById(R.id.test_user_first_name);
        mToolbar = findViewById(R.id.toolbar_main_activity);

        setUpToolbar();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }
    }
}
