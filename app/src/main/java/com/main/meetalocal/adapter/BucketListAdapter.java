package com.main.meetalocal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.meetalocal.GlideApp;
import com.main.meetalocal.R;
import com.main.meetalocal.database.CountryModel;
import com.main.meetalocal.database.Firebase;
import com.main.meetalocal.database.room.BucketListCountry;

import java.util.ArrayList;
import java.util.List;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder> {

    private CollectionReference firebaseReference;
    private StorageReference storageReference;
    private List<BucketListCountry> countries;
    private Context activityContext;

    public BucketListAdapter(List<BucketListCountry> countries, Context activityContext) {
        this.countries = countries;
        this.activityContext = activityContext;
        firebaseReference = FirebaseFirestore.getInstance().collection("countries");
        storageReference = FirebaseStorage.getInstance().getReference("/images/country_flags/");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_country, parent, false);
        return new BucketListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String countryName = countries.get(position).getCountryName();
        holder.countryName.setText(countryName);
        firebaseReference.document(countryName.toLowerCase()).get().addOnSuccessListener(successListener(holder.countryFlag));
    }

    /**
     * Get the correct flag image from the firebase storage and apply it to
     * an image view
     * @param countryFlagImageView the image view of the view holder
     * @return a success listener for the firebase firestore read
     */
    private OnSuccessListener<DocumentSnapshot> successListener(final ImageView countryFlagImageView) {
        return new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                CountryModel country = documentSnapshot.toObject(CountryModel.class);
                if(country != null) {
                    StorageReference childRef = storageReference.child(country.getRoundedFlagPath());
                    GlideApp.with(activityContext).load(childRef).into(countryFlagImageView);
                }
            }
        };
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
