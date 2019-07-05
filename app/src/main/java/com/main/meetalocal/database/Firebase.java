package com.main.meetalocal.database;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Firebase {

    private static final String USER_PATH = "users";
    private static final String LOCAL_USER_PATH = "locals";
    private FirebaseFirestore database;

    public Firebase() { database = FirebaseFirestore.getInstance(); }

    /**
     * Add a new user to the Firebase Firestore DB
     * @param user the user to be added
     */
    public void addUserToFirebase(User user) {
        database.collection(USER_PATH).document(new Authentication().getCurrentUserUid()).set(user);
    }

    /**
     * Add a new local user to the Firebase Firestore DB
     * @param localUser the local user to be added
     */
    public void addLocalUserToFirebase(Local localUser) {
        database.collection(LOCAL_USER_PATH).document(new Authentication().getCurrentUserUid()).set(localUser);
    }

    /**
     * Update a User if they make changes in their profiles
     * @param user the user to be updated
     */
    public void updateUser(User user) {
        Map<String, Object> update = new HashMap<>();

        update.put("firstName", user.getFirstName());
        update.put("surname", user.getSurname());
        update.put("country", user.getCountry());
        update.put("homeTown", user.getHomeTown());
        update.put("about", user.getAbout());
        update.put("languages", user.getLanguages());

        database.collection(USER_PATH).document(new Authentication().getCurrentUserUid())
                .set(update, SetOptions.merge());
    }
}
