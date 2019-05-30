package com.main.meetalocal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /*
    Start SignUpActivity Activity
     */
    public void onStartSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
