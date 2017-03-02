package com.ngallazzi.githubstargazers.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nicola on 2017-03-02.
 */

public class MyHttpClient {
   Retrofit sRetrofit;

    public MyHttpClient() {
       this.sRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
