package com.main.meetalocal.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class Firebase {

    private static final String USER_PATH = "users";
    private FirebaseFirestore database;

    public Firebase() { database = FirebaseFirestore.getInstance(); }

    public void addUserToFirebase(User user) {
        user.setuId(new Authentication().getCurrentUserUid());
        database.collection(USER_PATH).add(user);
    }
}
