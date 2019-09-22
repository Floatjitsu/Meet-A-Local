package com.main.meetalocal.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Firebase {

    private static final String USER_PATH = "users";
    private static final String LOCAL_USER_PATH = "locals";
    private static final String BUCKET_LIST_PATH = "bucketLists";
    //Firebase Firestore Reference
    private FirebaseFirestore firestoreDatabase;
    //Firebase Realtime DB Reference
    private FirebaseDatabase realtimeDatabase;

    public Firebase() {
        firestoreDatabase = FirebaseFirestore.getInstance();
        realtimeDatabase = FirebaseDatabase.getInstance();
    }

    /**
     * Add a new user to the Firebase Firestore DB
     * @param user the user to be added
     */
    public void addUserToFirebase(User user) {
        firestoreDatabase.collection(USER_PATH).document(new Authentication().getCurrentUserUid()).set(user);
    }

    /**
     * Add a new local user to the Firebase Firestore DB
     * @param localUser the local user to be added
     */
    public void addLocalUserToFirebase(Local localUser) {
        firestoreDatabase.collection(LOCAL_USER_PATH).document(new Authentication().getCurrentUserUid()).set(localUser);
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

        firestoreDatabase.collection(USER_PATH).document(new Authentication().getCurrentUserUid())
                .set(update, SetOptions.merge());
    }

    /**
     * Adds a country to the personal bucket list of the current user
     * @param countryName the country to be inserted
     */
    public void addCountryToBucketList(final String countryName) {
        final DatabaseReference bucketListRef = realtimeDatabase.getReference(BUCKET_LIST_PATH)
                .child(new Authentication().getCurrentUserUid());
        bucketListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Read all the countries which are already there,
                // add the new one and update the whole list
                ArrayList<String> bucketList = new ArrayList<>();
                for(DataSnapshot countries : dataSnapshot.getChildren()) {
                    if(countries.getValue() != null)
                        bucketList.add(countries.getValue().toString());
                }
                bucketList.add(countryName);
                bucketListRef.setValue(bucketList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //No need for the onCancelled listener
            }
        });
    }
}
