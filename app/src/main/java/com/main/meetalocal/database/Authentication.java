package com.main.meetalocal.database;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Authentication {

    private FirebaseUser currentUser;

    public Authentication() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void signOutCurrentUser() {
        FirebaseAuth.getInstance().signOut();
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

    public void logInUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public Uri getCurrentUserPhotoUrl() {
        return currentUser.getPhotoUrl();
    }

    public void setCurrentUserPhotoUrl(Uri photoUrl) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(photoUrl)
                .build();

        currentUser.updateProfile(profileUpdates);
    }
}
