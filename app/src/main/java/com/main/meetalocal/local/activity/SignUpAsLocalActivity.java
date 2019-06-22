package com.main.meetalocal.local.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.main.meetalocal.R;
import com.main.meetalocal.local.fragment.SignUpAsLocalFirstStepFragment;
import com.main.meetalocal.local.fragment.SignUpAsLocalSecondStepFragment;

public class SignUpAsLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_local);

        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_placeholder_sign_up_as_local_activity, new SignUpAsLocalFirstStepFragment())
            .commit();
    }
}
