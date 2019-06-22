package com.main.meetalocal.local.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
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

import com.main.meetalocal.R;
import com.main.meetalocal.database.Local;

import org.w3c.dom.Text;

public class SignUpAsLocalSecondStepFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private EditText mLocalIntroduction;
    private Local localUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_as_local_second_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        CheckBox checkBoxAccommodation = view.findViewById(R.id.checkbox_accommodation);
        checkBoxAccommodation.setOnCheckedChangeListener(this);

        CheckBox checkBoxAccompany = view.findViewById(R.id.checkbox_accompany);
        checkBoxAccompany.setOnCheckedChangeListener(this);

        CheckBox checkBoxGuidedTours = view.findViewById(R.id.check_box_guided_tours);
        checkBoxGuidedTours.setOnCheckedChangeListener(this);

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
        String introduction = mLocalIntroduction.getText().toString();
        if(TextUtils.isEmpty(introduction)) {
            localUser.setIntroduction(introduction);
        }

    }

    //Build a Local Object for the Database from Bundle and Fragment
    private Local buildLocal(Bundle bundle) {
        String email = bundle.getString("email");
        String firstName = bundle.getString("firstName");
        String surname = bundle.getString("surname");
        String country = bundle.getString("country");
        String homeTown = bundle.getString("homeTown");

        return new Local(firstName, surname, country, homeTown, email);
    }
}
