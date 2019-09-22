package com.main.meetalocal.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.main.meetalocal.R;
import com.main.meetalocal.user.activity.EditBucketListActivity;
import com.main.meetalocal.user.adapter.BucketListAdapter;
import com.main.meetalocal.database.room.BucketListCountry;
import com.main.meetalocal.user.viewmodel.BucketListViewModel;

import java.util.List;

public class BucketListFragment extends Fragment implements View.OnClickListener {

    private BucketListViewModel mBucketListViewModel;
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
        /*
        if(getActivity() != null) {
            mBucketListViewModel = ViewModelProviders.of(getActivity()).get(BucketListViewModel.class);
            mBucketListViewModel.getBucketList().observe(getActivity(), new Observer<List<BucketListCountry>>() {
                @Override
                public void onChanged(List<BucketListCountry> bucketListCountries) {
                    mBucketListAdapter = new BucketListAdapter(bucketListCountries, getActivity());
                    mRecyclerViewBucketList.setAdapter(mBucketListAdapter);
                }
            });
        } */

        FloatingActionButton editBucketListButton = view.findViewById(R.id.button_edit_bucket_list);
        editBucketListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_edit_bucket_list) {
            startActivity(new Intent(getActivity(), EditBucketListActivity.class));
        }
    }

    private void insertCountry(String countryName) {
        BucketListCountry bucketListCountry = new BucketListCountry(countryName);
        mBucketListViewModel.insert(bucketListCountry);
    }
}
