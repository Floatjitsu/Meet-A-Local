package com.main.meetalocal.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class Firebase {

    private static final String USER_PATH = "users";
    private static final String LOCAL_USER_PATH = "locals";
    private FirebaseFirestore database;

    public Firebase() { database = FirebaseFirestore.getInstance(); }

    public void addUserToFirebase(User user) {
        database.collection(USER_PATH).document(new Authentication().getCurrentUserUid()).set(user);
    }

    public void addLocalUserToFirebase(Local localUser) {
        database.collection(LOCAL_USER_PATH).document(new Authentication().getCurrentUserUid()).set(localUser);
    }
}
