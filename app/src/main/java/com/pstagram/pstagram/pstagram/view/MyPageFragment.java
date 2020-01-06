package com.pstagram.pstagram.pstagram.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import com.bumptech.glide.Glide;
import com.pstagram.pstagram.pstagram.EndlessScrollListener;
import com.pstagram.pstagram.pstagram.R;
import com.pstagram.pstagram.pstagram.RetrofitCreater;
import com.pstagram.pstagram.pstagram.UserIdManager;
import com.pstagram.pstagram.pstagram.adapter.ReviewAdapter;
import com.pstagram.pstagram.pstagram.databinding.FragmentMypageBinding;
import com.pstagram.pstagram.pstagram.model.ProfileOutputModel;
import com.pstagram.pstagram.pstagram.model.ReviewData;
import com.pstagram.pstagram.pstagram.model.ReviewOutputModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class MyPageFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener{
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;


    private FragmentMypageBinding binding;
    private Context context = getContext();
    private ReviewAdapter recyclerviewAdapter;
    private ArrayList<ReviewData> data = new ArrayList<>();
    private final int userId = UserIdManager.getInstance(context).getUserId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false);
        recyclerviewAdapter = new ReviewAdapter(getContext(), data, 0, 1, new EndlessScrollListener() {
            @Override
            public void reachedEnd(int page) {
                getReview(page);
            }
        });

        binding.toolbar.setTitle("");
        binding.appbar.addOnOffsetChangedListener(this);

//        container.setSupportActionBar(binding.toolbar);
        startAlphaAnimation(binding.title2, 0, View.INVISIBLE);

        //set avatar and cover

//        binding.image.setImageResource(R.drawable.cover);

        RetrofitCreater.newInstance().getService().getUserProfile(userId).enqueue(new Callback<ProfileOutputModel>() {
            @Override
            public void onResponse(Call<ProfileOutputModel> call, Response<ProfileOutputModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode().equals("success")){
                        ProfileOutputModel profile = response.body();
                        Glide.with(getActivity()).load(profile.getProfileUrl()).into(binding.avatar);
                        binding.username.setText(profile.getUsername());
                        binding.title2.setText(profile.getUsername());
                    }else{

                    }
                }
            }
            @Override
            public void onFailure(Call<ProfileOutputModel> call, Throwable t) {

            }
        });

        binding.recyclerview.setAdapter(recyclerviewAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        getReview(1);
        return binding.getRoot();
    }

    private void getReview(int page){
        RetrofitCreater.newInstance().getService().getReviewByUser(page,10,userId).enqueue(new Callback<ReviewOutputModel>() {
            @Override
            public void onResponse(Call<ReviewOutputModel> call, Response<ReviewOutputModel> response) {
                if(response.isSuccessful()){
                    data.addAll(response.body().getData());
                    recyclerviewAdapter.notifyDataSetChanged();
                    recyclerviewAdapter.setMaxPage(response.body().getMaxPage());
                }
            }

            @Override
            public void onFailure(Call<ReviewOutputModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(binding.title2, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(binding.title2, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(binding.linearLayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(binding.linearLayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
