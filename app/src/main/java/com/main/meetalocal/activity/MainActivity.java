package com.main.meetalocal.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.main.meetalocal.R;
import com.main.meetalocal.dialog.LogoutDialog;
import com.main.meetalocal.fragment.HomeFragment;
import com.main.meetalocal.viewmodel.ViewModelUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final int HEADER_INDEX = 0;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar mToolbar;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout_main_activity);
        mNavigationView = findViewById(R.id.navigation_view_main_activity);
        mToolbar = findViewById(R.id.toolbar_main_activity);

        //The HomeFragment is the first fragment a user sees when starting the app
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder_main_activity, new HomeFragment());
        fragmentTransaction.commit();

        setUpToolbar();
        setUpNavigationHeader();

        mNavigationView.getMenu().getItem(0).setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.menu_item_logout) {
            new LogoutDialog().show(getSupportFragmentManager(), "Logout Dialog");
            return true;
        }
        return false;
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
        LiveData<Task<DocumentSnapshot>> liveData = viewModelUser.getDataSnapshotLiveData();
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

    /**
     * Set up the Action Toolbar and set the menu icon as home indicator (left sided)
     */
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }
    }
}
