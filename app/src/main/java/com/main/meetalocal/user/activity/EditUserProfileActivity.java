package com.main.meetalocal.user.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.meetalocal.R;
import com.main.meetalocal.database.Authentication;
import com.main.meetalocal.database.Firebase;
import com.main.meetalocal.database.User;
import com.main.meetalocal.user.viewmodel.ViewModelUser;

import java.io.FileDescriptor;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserProfileActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    EditText mFirstName, mSurname, mCountry, mHomeTown, mAbout, mLanguages;
    CircleImageView mProfilePicture;
    Uri profilePicturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        mFirstName = findViewById(R.id.edit_text_edit_first_name);
        mSurname = findViewById(R.id.edit_text_edit_surname);
        mCountry = findViewById(R.id.edit_text_edit_country);
        mHomeTown = findViewById(R.id.edit_text_edit_home_town);
        mAbout = findViewById(R.id.edit_text_edit_about);
        mLanguages = findViewById(R.id.edit_text_edit_languages);
        mProfilePicture = findViewById(R.id.image_edit_profile_picture);

        Toolbar editProfileToolbar = findViewById(R.id.toolbar_edit_user_profile_activity);
        setSupportActionBar(editProfileToolbar);

        setUserObserver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_user_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_edit_user_profile_save) {
            updateUser();
            //upload new picture only if user selected a new one
            if(profilePicturePath != null) {
                uploadNewProfilePicture();
            }
            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            profilePicturePath = null;
            if(data != null) {
                profilePicturePath = data.getData();
                new CreateBitmapTask().execute(profilePicturePath);
            }
        }
    }

    /**
     * Set the LiveData Observer for users profile
     * After receiving the data the corresponding EditTexts will be assigned with values
     */
    private void setUserObserver() {
        ViewModelUser viewModelUser = ViewModelProviders.of(this).get(ViewModelUser.class);
        LiveData<Task<DocumentSnapshot>> liveData = viewModelUser.getDataSnapshotLiveData();
        liveData.observe(this, new Observer<Task<DocumentSnapshot>>() {
            @Override
            public void onChanged(Task<DocumentSnapshot> documentSnapshotTask) {
                if(documentSnapshotTask.isSuccessful()) {
                    DocumentSnapshot snapshot = documentSnapshotTask.getResult();
                    if(snapshot != null) {
                        mFirstName.setText(snapshot.getString("firstName"));
                        mSurname.setText(snapshot.getString("surname"));
                        mCountry.setText(snapshot.getString("country"));
                        mHomeTown.setText(snapshot.getString("homeTown"));
                        mAbout.setText(snapshot.getString("about"));
                        mLanguages.setText(snapshot.getString("languages"));
                        //TODO: Load profile picture of user
                    }
                }
            }
        });
    }

    //Build a User object from UI Elements to update a profile
    private User buildUserFromUi() {
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        String country = mCountry.getText().toString();
        String homeTown = mHomeTown.getText().toString();
        String languages = mLanguages.getText().toString();
        String about = mAbout.getText().toString();

        return new User(firstName, surname, country, homeTown, languages, about);
    }

    //Event Handler to select a new profile picture
    public void onEditProfilePicture(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    //Update User in Firebase Firestore DB
    private void updateUser() {
        User user = buildUserFromUi();
        if(profilePicturePath != null) {
            user.setPhotoUri(new Authentication().getCurrentUserUid() + "_" +
                    profilePicturePath.getLastPathSegment() + ".jpg");
        }
        new Firebase().updateUser(user);
    }

    //Upload the Image to the Firebase Storage
    private void uploadNewProfilePicture() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profilePicRef = storageReference.child("profile_pictures")
                .child(new Authentication().getCurrentUserUid() + "_" + profilePicturePath.getLastPathSegment() + ".jpg");
        profilePicRef.putFile(profilePicturePath);
    }


    /**
     * AsyncTask to display a Image from the storage as a new profile picture
     */
    @SuppressLint("StaticFieldLeak")
    private class CreateBitmapTask extends AsyncTask<Uri, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Uri... uris) {
            try {
                //Only one Uri will get converted
                return getBitmapFromUri(uris[0]);
            } catch (IOException ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                mProfilePicture.setImageBitmap(bitmap);
            } else {
                Toast.makeText(EditUserProfileActivity.this, "Couldn't pick image, please try again", Toast.LENGTH_SHORT).show();
            }
        }

        //Create a Bitmap out of a given uri to show it in a ImageView
        Bitmap getBitmapFromUri(Uri uri) throws IOException {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(uri, "r");
            if(parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                return image;
            }
            return null;
        }
    }
}
