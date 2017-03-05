package com.ngallazzi.githubstargazers.network.tasks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nicola on 2017-03-02.
 */

public class MyHttpClient {
    final String API_BASE_URL = "https://api.github.com/";
    Retrofit retrofit;

    public MyHttpClient() {
       this.retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
