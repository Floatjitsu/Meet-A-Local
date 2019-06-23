package com.main.meetalocal.local.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class SignUpAsLocalSecondStepFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private CheckBox mCheckBoxAccommodation, mCheckBoxAccompany, mCheckBoxGuidedTours;
    private EditText mLocalIntroduction;
    private Local localUser;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_as_local_second_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCheckBoxAccommodation = view.findViewById(R.id.checkbox_accommodation);
        mCheckBoxAccommodation.setOnCheckedChangeListener(this);

        mCheckBoxAccompany = view.findViewById(R.id.checkbox_accompany);
        mCheckBoxAccompany.setOnCheckedChangeListener(this);

        mCheckBoxGuidedTours = view.findViewById(R.id.check_box_guided_tours);
        mCheckBoxGuidedTours.setOnCheckedChangeListener(this);

        mLocalIntroduction = view.findViewById(R.id.edit_text_local_introduction);

        mProgressBar = view.findViewById(R.id.progress_bar_horizontal);

        if(getArguments() != null) {
            localUser = buildLocal(getArguments());
        }

        Button buttonFinishSignUp = view.findViewById(R.id.button_finish_sign_up);
        buttonFinishSignUp.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox_accommodation:
                localUser.setProvidesAccommodation(isChecked);
                break;
            case R.id.checkbox_accompany:
                localUser.setProvidesAccompany(isChecked);
                break;
            case R.id.check_box_guided_tours:
                localUser.setProvidesGuidedTours(isChecked);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        mProgressBar.setVisibility(View.VISIBLE);
        CheckBox [] checkBoxes = { mCheckBoxGuidedTours, mCheckBoxAccompany, mCheckBoxAccommodation };
        if(!Validator.validateLocalSignUpStepTwo(checkBoxes) && getActivity() != null) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            Toast.makeText(getActivity(), "Please select at least one Provision!", Toast.LENGTH_SHORT).show();
        } else {
            String introduction = mLocalIntroduction.getText().toString();
            if(!TextUtils.isEmpty(introduction)) {
                localUser.setIntroduction(introduction);
            }
            try {
                signUpLocal();
                new Firebase().addLocalUserToFirebase(localUser);
            } catch (Exception ex) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void signUpLocal() throws Exception {
        if(getArguments() != null) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    localUser.getEmail(), Password.decrypt(getArguments().getString(BundleConstants.PASSWORD)))
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
                    Toast.makeText(getActivity(), "User got created!", Toast.LENGTH_SHORT).show();
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

    //Build a Local Object for the Database from Bundle and Fragment
    private Local buildLocal(Bundle bundle) {
        String email = bundle.getString(BundleConstants.EMAIL);
        String firstName = bundle.getString(BundleConstants.FIRST_NAME);
        String surname = bundle.getString(BundleConstants.SURNAME);
        String country = bundle.getString(BundleConstants.COUNTRY);
        String homeTown = bundle.getString(BundleConstants.HOME_TOWN);

        return new Local(firstName, surname, country, homeTown, email);
    }
}
