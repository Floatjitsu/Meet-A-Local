package com.main.meetalocal.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.meetalocal.R;
import com.main.meetalocal.database.Authentication;
import com.main.meetalocal.user.activity.EditBucketListActivity;
import com.main.meetalocal.user.adapter.BucketListAdapter;
import com.main.meetalocal.user.viewmodel.BucketListViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class BucketListFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerViewBucketList;
    private BucketListAdapter mBucketListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bucket_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewBucketList = view.findViewById(R.id.recycler_view_bucket_list);
        mRecyclerViewBucketList.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Create BucketListViewModel and liveData to observe the users bucketList
        BucketListViewModel bucketListViewModel = ViewModelProviders.of(this).get(BucketListViewModel.class);
        LiveData<DataSnapshot> liveData = bucketListViewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                Log.d("LIVE_DATA", "live data call works");
                ArrayList<String> bucketList = new ArrayList<>();
                for(DataSnapshot country : dataSnapshot.getChildren()) {
                    //Add countries one by one to the ArrayList
                    bucketList.add(Objects.requireNonNull(country.getValue()).toString());
                }
                //If the ArrayList is not empty, create the RecyclerView and Adapter to show Countries inside the Fragment
                if(!bucketList.isEmpty()) {
                    mBucketListAdapter = new BucketListAdapter(bucketList, getActivity());
                    mRecyclerViewBucketList.setAdapter(mBucketListAdapter);
                }
            }
        });

        FloatingActionButton editBucketListButton = view.findViewById(R.id.button_edit_bucket_list);
        editBucketListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_edit_bucket_list) {
            startActivity(new Intent(getActivity(), EditBucketListActivity.class));
        }
    }
}
