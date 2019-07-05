package com.main.meetalocal.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.main.meetalocal.BundleConstants;
import com.main.meetalocal.R;
import com.main.meetalocal.database.User;
import com.main.meetalocal.user.activity.EditUserProfileActivity;
import com.main.meetalocal.user.activity.MainActivity;
import com.main.meetalocal.user.viewmodel.ViewModelUser;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView mFirstName, mCountry, mHomeTown, mAbout, mLanguages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFirstName = view.findViewById(R.id.text_profile_first_name);
        mCountry = view.findViewById(R.id.text_profile_country);
        mHomeTown = view.findViewById(R.id.text_profile_home_town);
        mAbout = view.findViewById(R.id.text_profile_user_about);
        mLanguages = view.findViewById(R.id.text_profile_languages);

        ImageButton imageButtonEditProfile = view.findViewById(R.id.image_button_edit_user_profile);
        imageButtonEditProfile.setOnClickListener(this);

        setUpUserProfile();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.image_button_edit_user_profile) {
            startActivity(new Intent(getActivity(), EditUserProfileActivity.class));
        }
    }

    /**
     * Call the ViewModelUser Observer, build a User Object with the received data
     * and populate them with the layout views
     */
    private void setUpUserProfile() {
        if(getActivity() != null) {
            ViewModelUser viewModelUser = ViewModelProviders.of(getActivity()).get(ViewModelUser.class);
            LiveData<Task<DocumentSnapshot>> liveData = viewModelUser.getDataSnapshotLiveData();
            liveData.observe(getActivity(), new Observer<Task<DocumentSnapshot>>() {
                @Override
                public void onChanged(Task<DocumentSnapshot> documentSnapshotTask) {
                    if(documentSnapshotTask.isSuccessful()) {
                        DocumentSnapshot snapshot = documentSnapshotTask.getResult();
                        if(snapshot != null) {
                            mFirstName.setText(snapshot.getString("firstName"));
                            mCountry.setText(snapshot.getString("country"));
                            mHomeTown.setText(snapshot.getString("homeTown"));
                            mAbout.setText(snapshot.getString("about"));
                            mLanguages.setText(snapshot.getString("languages"));
                        }
                    }
                }
            });
        }
    }
}
