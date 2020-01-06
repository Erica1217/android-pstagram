package com.pstagram.pstagram.pstagram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewOutputModel {
    @SerializedName("max_page")
    @Expose
    private Integer maxPage;
    @SerializedName("current_page")
    @Expose
    private String currentPage;
    @SerializedName("data")
    @Expose
    private List<ReviewData> data = null;

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public void setData(List<ReviewData> data) {
        this.data = data;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public List<ReviewData> getData() {
        return data;
    }
}
