package com.main.meetalocal.user.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.main.meetalocal.R;
import com.main.meetalocal.user.fragment.SignUpFirstStepFragment;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_placeholder_sign_up_activity, new SignUpFirstStepFragment())
                .commit();
    }
}
