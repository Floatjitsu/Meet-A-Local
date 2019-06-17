package com.main.meetalocal.local.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.meetalocal.R;
import com.main.meetalocal.Validator;

public class SignUpAsLocalFirstStepFragment extends Fragment implements View.OnClickListener {

    private EditText mEmail, mFirstName, mSurname, mCountry, mHomeTown;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_as_local_first_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mEmail = view.findViewById(R.id.edit_text_email_sign_up_local);
        mFirstName = view.findViewById(R.id.edit_text_first_name_local);
        mSurname = view.findViewById(R.id.edit_text_surname_local);
        mCountry = view.findViewById(R.id.edit_text_home_country_local);
        mHomeTown = view.findViewById(R.id.edit_text_home_town_local);

        Button nextStepButton = view.findViewById(R.id.button_next_step);
        nextStepButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        EditText [] userInputs = {mEmail, mFirstName, mSurname, mCountry, mHomeTown};
        if(view.getId() == R.id.button_next_step) {
           if(Validator.validateUserInputs(userInputs)) {
               
           }
        }
    }
}
