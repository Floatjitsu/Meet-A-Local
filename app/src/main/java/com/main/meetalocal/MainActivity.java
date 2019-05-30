package com.main.meetalocal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.main.meetalocal.viewmodel.ViewModelUser;

public class MainActivity extends AppCompatActivity {

    TextView mUserEmail, mUserFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserEmail = findViewById(R.id.test_user_email);
        mUserFirstName = findViewById(R.id.test_user_first_name);

    }
}
