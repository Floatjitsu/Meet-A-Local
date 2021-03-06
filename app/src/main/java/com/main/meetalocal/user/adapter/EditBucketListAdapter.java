package com.main.meetalocal.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.meetalocal.GlideApp;
import com.main.meetalocal.R;
import com.main.meetalocal.database.Authentication;
import com.main.meetalocal.database.CountryModel;
import com.main.meetalocal.database.Firebase;

import java.util.ArrayList;

public class EditBucketListAdapter extends RecyclerView.Adapter<EditBucketListAdapter.ViewHolder> {

    private Context activityContext;
    private StorageReference storageReference;
    private ArrayList<String> bucketList;
    private ArrayList<CountryModel> countries;
    private ArrayList<CountryModel> countriesCopy; //For filtering the RecyclerView
    private Firebase firebase;

    public EditBucketListAdapter(ArrayList<CountryModel> countries, Context activityContext) {
        this.countries = countries;
        this.activityContext = activityContext;
        storageReference = FirebaseStorage.getInstance().getReference("/images/country_flags/");
        countriesCopy = new ArrayList<>();
        countriesCopy.addAll(countries);
        firebase = new Firebase();
        bucketList = new ArrayList<>();

        //Add all countries from the users current Bucket List to bucketList
        DatabaseReference bucketListReference = FirebaseDatabase.getInstance().getReference("bucketLists");
        bucketListReference.child(new Authentication().getCurrentUserUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot country : dataSnapshot.getChildren()) {
                    if(country.getValue() != null)
                        bucketList.add(country.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public EditBucketListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EditBucketListAdapter.ViewHolder holder, int position) {
        holder.countryName.setText(countries.get(position).getCountryName());
        StorageReference ref = storageReference.child(countries.get(position).getRoundedFlagPath());
        GlideApp.with(activityContext).load(ref).into(holder.countryFlag);

        //Check if a country already is in the users bucket list and toggle the check mark
        if(bucketList.contains(countries.get(position).getCountryName())){
            holder.checkMark.setVisibility(View.VISIBLE);
        } else {
            holder.checkMark.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Filter the RecyclerView items (countries) by a given text
     * @param text filter query
     */
    public void filterCountryList(String text) {
        countries.clear();
        if(text.isEmpty()) {
            countries.addAll(countriesCopy);
        } else {
            //Search for countries which contain the filter query and update the list
            for(CountryModel country : countriesCopy) {
                if(country.getCountryName().contains(text) || country.getCountryName().toLowerCase().contains(text)) {
                    countries.add(country);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView countryName;
        ImageView countryFlag, checkMark;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.text_country_name);
            countryFlag = itemView.findViewById(R.id.image_country_flag_round);
            checkMark = itemView.findViewById(R.id.image_country_check_mark);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String country = countryName.getText().toString();
            //Country does not exist in bucket list
            if(checkMark.getVisibility() == View.INVISIBLE) {
                checkMark.setVisibility(View.VISIBLE);
                //Insert the new country and show success message
                firebase.addCountryToBucketList(country);
                Toast.makeText(activityContext,
                        "Added " + country + " to your Bucket List!",
                        Toast.LENGTH_SHORT)
                        .show();
            } else { //Country exists in bucket list
                checkMark.setVisibility(View.INVISIBLE);
                //Delete the country and show success message
                firebase.removeCountryFromBucketList(country);
                Toast.makeText(activityContext,
                        "Removed " + country + " from your Bucket List!",
                        Toast.LENGTH_SHORT)
                        .show();

            }
        }
    }
}
