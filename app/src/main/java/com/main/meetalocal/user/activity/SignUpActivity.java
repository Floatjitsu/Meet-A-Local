package com.main.meetalocal.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.main.meetalocal.R;
import com.main.meetalocal.user.database.Authentication;
import com.main.meetalocal.user.database.CountryModel;
import com.main.meetalocal.user.database.Firebase;
import com.main.meetalocal.user.database.User;
import com.main.meetalocal.Validator;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    EditText mEmail, mPassword, mFirstName, mSurname, mHomeTown, mPasswordConfirm;
    AutoCompleteTextView mAutoCompleteCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = findViewById(R.id.edit_text_email_sign_up);
        mPassword = findViewById(R.id.edit_text_password_sign_up);
        mPasswordConfirm = findViewById(R.id.edit_text_password_confirm);
        mFirstName = findViewById(R.id.edit_text_first_name);
        mAutoCompleteCountry = findViewById(R.id.auto_complete_text_home_country);
        mHomeTown = findViewById(R.id.edit_text_home_town);
        mSurname = findViewById(R.id.edit_text_surname);

        setAutoCompleteCountry();
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
        String country = mAutoCompleteCountry.getText().toString();
        String homeTown = mHomeTown.getText().toString();

        return new User(firstName, surname, country, homeTown, email);
    }

    //Check if the user inputs are valid
    private boolean isValid() {
        EditText [] userInputs = new EditText [] {
                mEmail, mFirstName, mSurname, mAutoCompleteCountry, mHomeTown
        };
        return Validator.validateUserInputs(userInputs) && Validator.validatePasswords(mPassword, mPasswordConfirm);
    }

    //Build the suggestions for the AutoCompleteTextView Home Country
    private void setAutoCompleteCountry() {
        final Context context = this;
        //Get all available countries from the FirebaseFirestore
        FirebaseFirestore.getInstance().collection("countries")
            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    List<CountryModel> countries =  queryDocumentSnapshots.toObjects(CountryModel.class);
                    String [] countryArray = new String [countries.size()];
                    for(int i = 0; i < countries.size(); i++) {
                        countryArray[i] = countries.get(i).getCountryName();
                    }
                    mAutoCompleteCountry.setAdapter(new ArrayAdapter<>(context, android.R.layout.select_dialog_item, countryArray));
                }
            }
        });
    }
}
