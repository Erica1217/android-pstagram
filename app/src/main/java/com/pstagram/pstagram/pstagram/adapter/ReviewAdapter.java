package com.pstagram.pstagram.pstagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.pstagram.pstagram.pstagram.EndlessScrollListener;
import com.pstagram.pstagram.pstagram.databinding.LayoutReviewBinding;
import com.pstagram.pstagram.pstagram.model.ReviewData;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private EndlessScrollListener listener;

    private ArrayList<ReviewData> data;
    private int maxPage;
    private int currentPage;
    private Context context;

    public ReviewAdapter(Context context, ArrayList<ReviewData> data, int maxPage, int currentPage, EndlessScrollListener listener) {
        this.data = data;
        this.context=context;
        this.maxPage = maxPage;
        this.currentPage = currentPage;
        this.listener = listener;
    }

    public void setMaxPage(int maxPage){
        this.maxPage = maxPage;
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutReviewBinding binding = LayoutReviewBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int i) {
        ReviewData item = data.get(i);
        holder.binding.username.setText(item.getUsername());
        holder.binding.contents.setText(item.getContent());
        holder.binding.createdAt.setText(item.getCreatedAt());
        holder.binding.ratingBar.setRating(item.getRate());
        holder.binding.productName.setText(item.getProductName());
        Glide.with(context)
                .load(item.getProfileUrl())
                .into(holder.binding.profile);
        Glide.with(context)
                .load(item.getPhotoUrl())
                .into(holder.binding.img);

        if(data.size()-3 < i && maxPage > currentPage){
            listener.reachedEnd(++currentPage);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class ReviewViewHolder extends RecyclerView.ViewHolder {
    LayoutReviewBinding binding;
    public ReviewViewHolder(LayoutReviewBinding binding) {
        super(binding.getRoot());
        this.binding= binding;
    }
}
