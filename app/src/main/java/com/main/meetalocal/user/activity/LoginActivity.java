package com.main.meetalocal.user.activity;

import android.app.Dialog;
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
import com.main.meetalocal.R;
import com.main.meetalocal.local.activity.SignUpAsLocalActivity;
import com.main.meetalocal.user.database.Authentication;

public class LoginActivity extends AppCompatActivity {

    Dialog mFullscreenDialog;
    Authentication mAuth;
    EditText mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.edit_text_email);
        mPassword = findViewById(R.id.edit_text_password);

        mFullscreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mFullscreenDialog.setContentView(R.layout.dialog_full_screen_login);

        mAuth = new Authentication();

        if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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
            mFullscreenDialog.show();
            mAuth.logInUser(mEmail.getText().toString(), mPassword.getText().toString(), onLoginComplete());
        }
    }

    /*
    OnCompleteListener for the Login action
     */
    private OnCompleteListener<AuthResult> onLoginComplete() {
        final Context context = this;
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(context, MainActivity.class));
                    mFullscreenDialog.dismiss();
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


    public void onSignUpAsLocal(View view) {
        startActivity(new Intent(this, SignUpAsLocalActivity.class));
    }
}
