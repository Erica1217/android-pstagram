package com.pstagram.pstagram.pstagram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewData {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("profile_url")
    @Expose
    private String profileUrl;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;
    @SerializedName("review_id")
    @Expose
    private String reviewId;
    @SerializedName("rate")
    @Expose
    private int rate;
    @SerializedName("product_name")
    @Expose
    private String productName;

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {

        return productName;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {

        return rate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

}
