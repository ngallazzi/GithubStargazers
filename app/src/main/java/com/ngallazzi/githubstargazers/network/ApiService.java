package com.ngallazzi.githubstargazers.network;

import com.ngallazzi.githubstargazers.models.Stargazer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nicola on 2017-03-02.
 */

public interface ApiService {
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json"
    })
    @GET("repos/{owner}/{repo}/stargazers")
    Call<ArrayList<Stargazer>> getStargazers(@Path("owner") String owner, @Path("repo") String repository);
}
