package com.main.meetalocal.local.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.main.meetalocal.R;
import com.main.meetalocal.Validator;
import com.main.meetalocal.database.CountryModel;
import com.main.meetalocal.database.Firebase;
import com.main.meetalocal.database.Local;
import com.main.meetalocal.database.User;

import java.util.List;

public class SignUpAsLocalFirstStepFragment extends Fragment implements View.OnClickListener {

    private EditText mEmail, mFirstName, mSurname, mHomeTown, mPassword, mPasswordConfirm;
    private AutoCompleteTextView mAutoCompleteCountry;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_as_local_first_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mEmail = view.findViewById(R.id.edit_text_email_sign_up_local);
        mFirstName = view.findViewById(R.id.edit_text_first_name_local);
        mSurname = view.findViewById(R.id.edit_text_surname_local);
        mHomeTown = view.findViewById(R.id.edit_text_home_town_local);
        mAutoCompleteCountry = view.findViewById(R.id.auto_complete_text_home_country_local);
        mPassword = view.findViewById(R.id.edit_text_password_local);
        mPasswordConfirm = view.findViewById(R.id.edit_text_password_confirm_local);
        mProgressBar = view.findViewById(R.id.progress_bar_horizontal);

        setAutoCompleteCountry();

        Button nextStepButton = view.findViewById(R.id.button_next_step);
        nextStepButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        EditText [] userInputs = {mEmail, mFirstName, mSurname, mAutoCompleteCountry, mHomeTown};
        if(view.getId() == R.id.button_next_step) {
           if(Validator.validateUserInputs(userInputs) && Validator.validatePasswords(mPassword, mPasswordConfirm)) {
               if(getActivity() != null) {
                   mProgressBar.setVisibility(View.VISIBLE);
                   FirebaseAuth.getInstance().createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                        .addOnCompleteListener(onCompleteListener())
                        .addOnFailureListener(onFailureListener());
               }
           } 
        }
    }

    //OnCompleteListener for the sign up in Firebase
    private OnCompleteListener<AuthResult> onCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Create Bundle out of user Inputs and start step 2 fragment
                SignUpAsLocalSecondStepFragment secondStepFragment = new SignUpAsLocalSecondStepFragment();
                secondStepFragment.setArguments(buildUserBundle());
                if(getFragmentManager() != null && task.isSuccessful()) {
                    new Firebase().addLocalUserToFirebase(buildLocalUser());
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_placeholder_sign_up_as_local_activity, secondStepFragment)
                            .commit();
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    //OnFailureListener for the sign up in Firebase
    private OnFailureListener onFailureListener() {
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(getActivity() != null) {
                    Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    //Build the suggestions for the AutoCompleteTextView Home Country
    private void setAutoCompleteCountry() {
        final Context context = getActivity();
        //Get all available countries from the FirebaseFirestore
        FirebaseFirestore.getInstance().collection("countries").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    List<CountryModel> countries =  queryDocumentSnapshots.toObjects(CountryModel.class);
                    String [] countryArray = new String [countries.size()];
                    for(int i = 0; i < countries.size(); i++) {
                        countryArray[i] = countries.get(i).getCountryName();
                    }
                    if(context != null)
                        mAutoCompleteCountry.setAdapter(new ArrayAdapter<>(context, android.R.layout.select_dialog_item, countryArray));
                }
            }
        });
    }

    //Build a local user object from the user inputs for signing them up
    private Local buildLocalUser() {
        String email = mEmail.getText().toString();
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        String country = mAutoCompleteCountry.getText().toString();
        String homeTown = mHomeTown.getText().toString();

        return new Local(firstName, surname, country, homeTown, email);
    }

    //Build a Bundle Object out of the user inputs for the second step fragment
    private Bundle buildUserBundle() {
        String email = mEmail.getText().toString();
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        String country = mAutoCompleteCountry.getText().toString();
        String homeTown = mHomeTown.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("firstName", firstName);
        bundle.putString("surname", surname);
        bundle.putString("country", country);
        bundle.putString("homeTown", homeTown);

        return bundle;
    }
}
