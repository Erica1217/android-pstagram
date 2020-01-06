package com.pstagram.pstagram.pstagram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreater {
    static RetrofitCreater _instance;
    private final String BASE_URL = "http://192.168.43.61/pstagram/api/";

    private Retrofit retrofit;
    private iHttpService service;

    private RetrofitCreater() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))

                .build();

         service = retrofit.create(iHttpService.class);

    }

    public static RetrofitCreater newInstance() {
        if (_instance == null) {
            _instance = new RetrofitCreater();
        }
        return _instance;
    }

    public iHttpService getService(){
        return service;
    }
}
