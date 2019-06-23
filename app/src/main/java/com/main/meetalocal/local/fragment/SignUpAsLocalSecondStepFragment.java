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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.meetalocal.BundleConstants;
import com.main.meetalocal.R;
import com.main.meetalocal.Validator;
import com.main.meetalocal.database.Firebase;
import com.main.meetalocal.database.Local;

public class SignUpAsLocalSecondStepFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private CheckBox mCheckBoxAccommodation, mCheckBoxAccompany, mCheckBoxGuidedTours;
    private EditText mLocalIntroduction;
    private Local localUser;

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
            new Firebase().addLocalUserToFirebase(localUser);
        }
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
