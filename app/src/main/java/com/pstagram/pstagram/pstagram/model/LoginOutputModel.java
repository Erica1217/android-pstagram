package com.pstagram.pstagram.pstagram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginOutputModel {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_url")
    @Expose
    private String profileUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("msg")
    @Expose
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {

        return msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
