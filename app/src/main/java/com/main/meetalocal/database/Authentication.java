package com.main.meetalocal.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Authentication {

    private FirebaseUser currentUser;

    public Authentication() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public String getCurrentUserUid() {
        return currentUser.getUid();
    }

    public String getCurrentUserDisplayName() {
        return currentUser.getDisplayName();
    }

    public void setCurrentUserDisplayName(String displayName) {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName).build();

        currentUser.updateProfile(profileUpdate);
    }
}
