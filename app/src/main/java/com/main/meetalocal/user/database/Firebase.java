package com.main.meetalocal.user.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class Firebase {

    private static final String USER_PATH = "users";
    private FirebaseFirestore database;

    public Firebase() { database = FirebaseFirestore.getInstance(); }

    public void addUserToFirebase(User user) {
        database.collection(USER_PATH).document(new Authentication().getCurrentUserUid()).set(user);
    }
}
