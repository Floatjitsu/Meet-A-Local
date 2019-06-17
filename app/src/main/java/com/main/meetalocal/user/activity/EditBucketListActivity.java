package com.main.meetalocal.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.main.meetalocal.R;
import com.main.meetalocal.user.adapter.EditBucketListAdapter;
import com.main.meetalocal.user.database.CountryModel;

import java.util.ArrayList;

public class EditBucketListActivity extends AppCompatActivity {

    Context mContext = this;
    SearchView mSearchView;
    RecyclerView mRecyclerViewCountries;
    EditBucketListAdapter mEditBucketListAdapter;
    ArrayList<CountryModel> mCountryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bucket_list);

        mRecyclerViewCountries = findViewById(R.id.recycler_view_countries);
        mCountryList = new ArrayList<>();

        setUpRecyclerView();

        mEditBucketListAdapter = new EditBucketListAdapter(new ArrayList<CountryModel>(), mContext);

        mSearchView = findViewById(R.id.search_view_edit_bucket_list_activity);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mEditBucketListAdapter.filterCountryList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mEditBucketListAdapter.filterCountryList(newText);
                return true;
            }
        });
    }

    /*
    Set up the RecyclerView with every possible country
     */
    private void setUpRecyclerView() {
        mRecyclerViewCountries.setLayoutManager(new LinearLayoutManager(this));
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("countries").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult() != null) {
                            for(DocumentSnapshot document : task.getResult()) {
                                CountryModel country = new CountryModel(document.getString("countryName"),
                                        document.getString("roundedFlagPath"));
                                mCountryList.add(country);
                            }
                            mEditBucketListAdapter = new EditBucketListAdapter(mCountryList, mContext);
                            mRecyclerViewCountries.setAdapter(mEditBucketListAdapter);
                        }
                    }
                });
    }
}
