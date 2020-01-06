package com.pstagram.pstagram.pstagram;

import com.pstagram.pstagram.pstagram.model.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface iHttpService {
    @POST("profile/register.php")
    public Call<RegisterOutputModel> register(@Body RegisterInputModel input);

    @POST("profile/login.php")
    public Call<LoginOutputModel> login(@Body LoginInputModel loginInputModel);

    @GET("review/review.php")
    public Call<ReviewOutputModel> getReviews(@Query("page")int page, @Query("item") int item);

    @GET("review/review.php")
    public Call<ReviewOutputModel> getReviewByUser(@Query("page")int page, @Query("item")int item, @Query("user_id")int userId);

    @Multipart
    @POST("review/review_upload.php")
    public Call<UploadOutputModel> uploadReview(@PartMap Map<String, RequestBody> requestBodys, @Part MultipartBody.Part file);

    @GET("profile/profile.php")
    public Call<ProfileOutputModel> getUserProfile(@Query("user_id")int userId);



}