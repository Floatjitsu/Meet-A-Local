package com.main.meetalocal.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.main.meetalocal.BundleConstants;
import com.main.meetalocal.R;
import com.main.meetalocal.Validator;
import com.main.meetalocal.database.Firebase;
import com.main.meetalocal.database.Local;
import com.main.meetalocal.database.Password;
import com.main.meetalocal.database.User;
import com.main.meetalocal.user.activity.MainActivity;

public class SignUpSecondStepFragment extends Fragment implements View.OnClickListener {

    private EditText mUserIntro, mLanguages;
    private User user;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_second_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mProgressBar = view.findViewById(R.id.progress_bar_horizontal);
        mUserIntro = view.findViewById(R.id.edit_text_introduction);
        mLanguages = view.findViewById(R.id.edit_text_languages);

        if(getArguments() != null) {
            user = buildUser(getArguments());
        }

        Button buttonFinishSignUp = view.findViewById(R.id.button_finish_sign_up);
        buttonFinishSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(Validator.validateSignUpLanguageInput(mLanguages) && getActivity() != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            String about = mUserIntro.getText().toString();
            String languages = mLanguages.getText().toString();
            if(!TextUtils.isEmpty(about)) {
                user.setAbout(about);
            }
            if(!TextUtils.isEmpty(languages)) {
                user.setLanguages(languages);
            }
            try {
                signUpUser();
            } catch (Exception ex) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void signUpUser() throws Exception {
        if(getArguments() != null) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    user.getEmail(), Password.decrypt(getArguments().getString(BundleConstants.PASSWORD)))
                    .addOnCompleteListener(onCompleteListener())
                    .addOnFailureListener(onFailureListener());
        }
    }

    //OnCompleteListener for the sign up in Firebase
    private OnCompleteListener<AuthResult> onCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    new Firebase().addUserToFirebase(user);
                    Toast.makeText(getActivity(), "User got created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    //OnFailureListener for the sign up in Firebase
    private OnFailureListener onFailureListener() {
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(getActivity() != null) {
                    Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    //Build a User Object for the Database from Bundle and Fragment
    private User buildUser(Bundle bundle) {
        String email = bundle.getString(BundleConstants.EMAIL);
        String firstName = bundle.getString(BundleConstants.FIRST_NAME);
        String surname = bundle.getString(BundleConstants.SURNAME);
        String country = bundle.getString(BundleConstants.COUNTRY);
        String homeTown = bundle.getString(BundleConstants.HOME_TOWN);

        return new User(firstName, surname, country, homeTown, email);
    }
}
