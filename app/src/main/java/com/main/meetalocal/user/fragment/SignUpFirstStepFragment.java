package com.main.meetalocal.user.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.main.meetalocal.BundleConstants;
import com.main.meetalocal.R;
import com.main.meetalocal.Validator;
import com.main.meetalocal.database.CountryModel;
import com.main.meetalocal.database.Password;
import com.main.meetalocal.local.fragment.SignUpAsLocalSecondStepFragment;

import java.util.List;

public class SignUpFirstStepFragment extends Fragment implements View.OnClickListener {

    private EditText mEmail, mPassword, mFirstName, mSurname, mHomeTown, mPasswordConfirm;
    private AutoCompleteTextView mAutoCompleteCountry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_first_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mEmail = view.findViewById(R.id.edit_text_email_sign_up);
        mPassword = view.findViewById(R.id.edit_text_password_sign_up);
        mPasswordConfirm = view.findViewById(R.id.edit_text_password_confirm);
        mFirstName = view.findViewById(R.id.edit_text_first_name);
        mAutoCompleteCountry = view.findViewById(R.id.auto_complete_text_home_country);
        mHomeTown = view.findViewById(R.id.edit_text_home_town);
        mSurname = view.findViewById(R.id.edit_text_surname);
        Button buttonNextStep = view.findViewById(R.id.button_next_step);
        buttonNextStep.setOnClickListener(this);

        setAutoCompleteCountry();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_next_step && isValid()) {
            if(getActivity() != null && getFragmentManager() != null) {
                try {
                    SignUpSecondStepFragment secondStepFragment = new SignUpSecondStepFragment();
                    secondStepFragment.setArguments(buildUserBundle());
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_placeholder_sign_up_activity, secondStepFragment)
                            .addToBackStack(null)
                            .commit();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Check if the user inputs are valid
    private boolean isValid() {
        EditText [] userInputs = new EditText [] {
                mEmail, mFirstName, mSurname, mAutoCompleteCountry, mHomeTown
        };
        return Validator.validateUserInputs(userInputs) && Validator.validatePasswords(mPassword, mPasswordConfirm);
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

    //Build a Bundle Object out of the user inputs for the second step fragment
    private Bundle buildUserBundle() throws Exception {
        String email = mEmail.getText().toString();
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        String country = mAutoCompleteCountry.getText().toString();
        String homeTown = mHomeTown.getText().toString();
        String password = mPassword.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(BundleConstants.EMAIL, email);
        bundle.putString(BundleConstants.FIRST_NAME, firstName);
        bundle.putString(BundleConstants.SURNAME, surname);
        bundle.putString(BundleConstants.COUNTRY, country);
        bundle.putString(BundleConstants.HOME_TOWN, homeTown);
        bundle.putString(BundleConstants.PASSWORD, Password.encrypt(password));

        return bundle;
    }


}
