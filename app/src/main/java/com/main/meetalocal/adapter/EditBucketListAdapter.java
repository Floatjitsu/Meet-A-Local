package com.main.meetalocal.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.meetalocal.GlideApp;
import com.main.meetalocal.R;
import com.main.meetalocal.database.CountryModel;

import java.util.ArrayList;

public class EditBucketListAdapter extends RecyclerView.Adapter<EditBucketListAdapter.ViewHolder> {

    private Context activityContext;
    private StorageReference storageReference;
    private ArrayList<CountryModel> countries;
    private ArrayList<CountryModel> countriesCopy; //For filtering the RecyclerView

    public EditBucketListAdapter(ArrayList<CountryModel> countries, Context activityContext) {
        this.countries = countries;
        this.activityContext = activityContext;
        storageReference = FirebaseStorage.getInstance().getReference("/images/country_flags/");
        countriesCopy = new ArrayList<>();
        countriesCopy.addAll(countries);
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
    }

    /**
     * Filter the RecyclerView items by a given text
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryName;
        ImageView countryFlag;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.text_country_name);
            countryFlag = itemView.findViewById(R.id.image_country_flag_round);
        }
    }
}
