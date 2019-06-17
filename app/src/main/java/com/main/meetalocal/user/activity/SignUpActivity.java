package com.main.meetalocal.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.main.meetalocal.R;
import com.main.meetalocal.user.database.Authentication;
import com.main.meetalocal.user.database.Firebase;
import com.main.meetalocal.user.database.User;

public class SignUpActivity extends AppCompatActivity {

    EditText mEmail, mPassword, mFirstName, mSurname, mCountry, mHomeTown, mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = findViewById(R.id.edit_text_email_sign_up);
        mPassword = findViewById(R.id.edit_text_password_sign_up);
        mPasswordConfirm = findViewById(R.id.edit_text_password_confirm);
        mFirstName = findViewById(R.id.edit_text_first_name);
        mCountry = findViewById(R.id.edit_text_home_country);
        mHomeTown = findViewById(R.id.edit_text_home_town);
        mSurname = findViewById(R.id.edit_text_surname);
    }

    public void onSignUp(View view) {
        //Only continue sign up if page is valid
        if(isValid()) {
            final Context context = this;
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                new Authentication().setCurrentUserDisplayName(mFirstName.getText().toString());
                                new Firebase().addUserToFirebase(buildUser());
                                startActivity(new Intent(context, MainActivity.class));
                            }
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(100);
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    //Build User from UI Elements for Database
    private User buildUser() {
        String email = mEmail.getText().toString();
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        String country = mCountry.getText().toString();
        String homeTown = mHomeTown.getText().toString();

        return new User(firstName, surname, country, homeTown, email);
    }

    //Check if the user inputs are valid
    private boolean isValid() {
        EditText [] userInputs = new EditText [] {
                mEmail, mFirstName, mSurname, mCountry, mHomeTown,
                mPassword, mPasswordConfirm
        };
        boolean isValid = true;
        for(EditText editText : userInputs) {
            if(TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError("This field is required!");
                isValid = false;
            }
        }
        if(!mPassword.getText().toString().equals(mPasswordConfirm.getText().toString())) {
            mPasswordConfirm.setError("Passwords doesn't match!");
            isValid = false;
        }
        return isValid;
    }
}
