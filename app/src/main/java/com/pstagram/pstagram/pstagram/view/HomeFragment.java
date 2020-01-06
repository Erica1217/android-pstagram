package com.pstagram.pstagram.pstagram.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pstagram.pstagram.pstagram.EndlessScrollListener;
import com.pstagram.pstagram.pstagram.R;
import com.pstagram.pstagram.pstagram.RetrofitCreater;
import com.pstagram.pstagram.pstagram.adapter.ReviewAdapter;
import com.pstagram.pstagram.pstagram.databinding.FragmentHomeBinding;
import com.pstagram.pstagram.pstagram.model.ReviewData;
import com.pstagram.pstagram.pstagram.model.ReviewOutputModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    FragmentHomeBinding binding;
    ReviewAdapter adapter;
    ArrayList<ReviewData> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReviewAdapter(getContext(), data, 0, 1, new EndlessScrollListener() {
            @Override
            public void reachedEnd(int page) {
                getReview(page);
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.recyclerview.setAdapter(adapter);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),WriteActivity.class));
            }
        });
        getReview(1);
        return view;
    }

    private void getReview(int page){
        RetrofitCreater.newInstance().getService().getReviews(page,10).enqueue(new Callback<ReviewOutputModel>() {
            @Override
            public void onResponse(Call<ReviewOutputModel> call, Response<ReviewOutputModel> response) {
                if(response.isSuccessful()){
                    data.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                    adapter.setMaxPage(response.body().getMaxPage());
                }
            }

            @Override
            public void onFailure(Call<ReviewOutputModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onRefresh() {
        data.clear();
        getReview(1);
        binding.swipeRefreshLayout.setRefreshing(false);
    }
}
