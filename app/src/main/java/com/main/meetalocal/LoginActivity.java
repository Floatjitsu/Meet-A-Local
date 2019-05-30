package com.main.meetalocal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.main.meetalocal.database.Authentication;

public class LoginActivity extends AppCompatActivity {

    Authentication auth;
    EditText mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.edit_text_email);
        mPassword = findViewById(R.id.edit_text_password);

        auth = new Authentication();

        if(auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    /*
    Start SignUpActivity Activity
     */
    public void onStartSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    /*
    Login User
     */
    public void onLogin(View view) {
        if(isValid()) {
            auth.logInUser(mEmail.getText().toString(), mPassword.getText().toString(), onLoginComplete());
        }
    }

    /*
    Listener for the login action
     */
    private OnCompleteListener<AuthResult> onLoginComplete() {
        final Context context = this;
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(context, MainActivity.class));
                } else {
                    Toast.makeText(context, "We couldn't find a user with the given email and password!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    /*
    Check if user input is valid
     */
    private boolean isValid() {
        boolean isValid = true;
        if(TextUtils.isEmpty(mEmail.getText().toString())) {
            isValid = false;
            mEmail.setError("Please fill in your email address");
        }
        if(TextUtils.isEmpty(mPassword.getText().toString())) {
            isValid = false;
            mPassword.setError("Please fill in your password");
        }
        return isValid;
    }


}
