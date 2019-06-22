package com.main.meetalocal.local.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.main.meetalocal.R;
import com.main.meetalocal.Validator;
import com.main.meetalocal.database.CountryModel;

import java.util.List;

public class SignUpAsLocalFirstStepFragment extends Fragment implements View.OnClickListener {

    private EditText mEmail, mFirstName, mSurname, mHomeTown, mPassword, mPasswordConfirm;
    private AutoCompleteTextView mAutoCompleteCountry;

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

        setAutoCompleteCountry();

        Button nextStepButton = view.findViewById(R.id.button_next_step);
        nextStepButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        EditText [] userInputs = {mEmail, mFirstName, mSurname, mAutoCompleteCountry, mHomeTown};
        if(view.getId() == R.id.button_next_step) {
           if(Validator.validateUserInputs(userInputs) && Validator.validatePasswords(mPassword, mPasswordConfirm)) {
               if(getFragmentManager() != null) {
                   getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_placeholder_sign_up_as_local_activity, new SignUpAsLocalSecondStepFragment())
                            .commit();
               }
           }
        }
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
}
