package com.main.meetalocal.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    final int HEADER_INDEX = 0;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout_main_activity);
        mNavigationView = findViewById(R.id.navigation_view_main_activity);
        mToolbar = findViewById(R.id.toolbar_main_activity);

        setUpToolbar();
        setUpNavigationHeader();

        mNavigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the NavigationDrawerHeader with the users email, first name and surname
     */
    private void setUpNavigationHeader() {
        View drawerHeader = mNavigationView.getHeaderView(HEADER_INDEX);
        final TextView userEmailHeader = drawerHeader.findViewById(R.id.text_navigation_header_email);
        final TextView userFirstNameSurnameHeader = drawerHeader.findViewById(R.id.text_navigation_header_names);
        setUserObserver(userEmailHeader, userFirstNameSurnameHeader);
    }

    /**
     * Set the LiveData Observer for users email, first name and surname
     * After receiving the data the corresponding TextViews will be assigned with values
     * @param userEmail TextView for the users email
     * @param userFirstNameSurname TextView for the users first- and surname
     */
    private void setUserObserver(final TextView userEmail, final TextView userFirstNameSurname) {
        ViewModelUser viewModelUser = ViewModelProviders.of(this).get(ViewModelUser.class);
        LiveData<Task<DocumentSnapshot>> liveData = viewModelUser.getdataSnapshotLiveData();
        liveData.observe(this, new Observer<Task<DocumentSnapshot>>() {
            @Override
            public void onChanged(Task<DocumentSnapshot> documentSnapshotTask) {
                if(documentSnapshotTask.isSuccessful()) {
                    DocumentSnapshot snapshot = documentSnapshotTask.getResult();
                    if(snapshot != null) {
                        userEmail.setText(Objects.requireNonNull(snapshot.get("email")).toString());
                        String firstNameSurname = snapshot.get("firstName") + " " + snapshot.get("surname");
                        userFirstNameSurname.setText(firstNameSurname);
                    }
                }
            }
        });
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
