package com.main.meetalocal.local.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.meetalocal.R;

public class SignUpAsLocalSecondStepFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

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
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {

    }
}
