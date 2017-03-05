package com.ngallazzi.githubstargazers.network;

import com.ngallazzi.githubstargazers.models.Repo;
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

public interface GitHub {
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json"
    })
    @GET("repos/{owner}/{repo}/stargazers")
    Call<ArrayList<Stargazer>> getStargazers(@Path("owner") String owner, @Path("repo") String repository,@Query("page") int page);
    @GET("users/{owner}/repos")
    Call<ArrayList<Repo>> getRepos(@Path("owner") String owner);
}
