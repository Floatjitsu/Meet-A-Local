package com.main.meetalocal.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.main.meetalocal.R;
import com.main.meetalocal.database.User;
import com.main.meetalocal.user.activity.MainActivity;
import com.main.meetalocal.user.viewmodel.ViewModelUser;

public class ProfileFragment extends Fragment {

    private TextView mFirstName, mCountry, mHomeTown;

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

        setUpUserProfile();
    }

    /**
     * Call the ViewModelUser Observer, build a User Object with the received data
     * and populate them with the layout views
     */
    private void setUpUserProfile() {
        final User user = new User();
        if(getActivity() != null) {
            ViewModelUser viewModelUser = ViewModelProviders.of(getActivity()).get(ViewModelUser.class);
            LiveData<Task<DocumentSnapshot>> liveData = viewModelUser.getDataSnapshotLiveData();
            liveData.observe(getActivity(), new Observer<Task<DocumentSnapshot>>() {
                @Override
                public void onChanged(Task<DocumentSnapshot> documentSnapshotTask) {
                    if(documentSnapshotTask.isSuccessful()) {
                        DocumentSnapshot snapshot = documentSnapshotTask.getResult();
                        if(snapshot != null) {
                            user.setCountry(snapshot.getString("country"));
                            user.setFirstName(snapshot.getString("firstName"));
                            user.setHomeTown(snapshot.getString("homeTown"));
                            setUpUserProfile(user);
                        }
                    }
                }
            });
        }
    }

    /**
     * Populate data from the view model with layout views
     * @param user the user object with the proper data inside
     */
    private void setUpUserProfile(User user) {
        mFirstName.setText(user.getFirstName());
        mCountry.setText(user.getCountry());
        mHomeTown.setText(user.getHomeTown());
    }
}
