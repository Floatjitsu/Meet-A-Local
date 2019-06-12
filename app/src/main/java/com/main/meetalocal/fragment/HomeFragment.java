package com.main.meetalocal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.main.meetalocal.R;
import com.main.meetalocal.database.CountryModel;

import java.util.ArrayList;
import java.util.Set;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        final String [] countries = {
                "Argentina",
                "Armenia",
                "Australia",
                "Austria",
                "Azerbaijan",
                "Bangladesh",
                "Belgium",
                "Belize",
                "Bolivia",
                "Bosnia and Herzegovina",
                "Brazil",
                "Bulgaria",
                "Cambodia",
                "Canada",
                "Chile",
                "Colombia",
                "Costa Rica",
                "Croatia",
                "Cuba",
                "Cyprus",
                "Czech Republic",
                "Denmark",
                "Egypt",
                "Estonia",
                "Finland",
                "France",
                "Georgia",
                "Germany",
                "Greece",

        };

        final String [] testCountries = new String [] {"Argentina", "Armenia"};
        final String dataNameEnd = "-flag-round.png";

        Button button = view.findViewById(R.id.button_insert_countries);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryModel country = new CountryModel("test", "test");
                for(int i = 0; i < countries.length; i++) {
                    country.setCountryName(countries[i]);
                    country.setRoundedFlagPath(countries[i].toLowerCase() + dataNameEnd);
                    database.collection("countries").document(countries[i].toLowerCase()).set(country);
                }
            }
        }); */
    }
}
