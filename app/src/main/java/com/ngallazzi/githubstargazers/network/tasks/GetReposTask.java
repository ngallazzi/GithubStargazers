package com.ngallazzi.githubstargazers.network.tasks;

import android.content.Context;

import com.ngallazzi.githubstargazers.models.Repo;
import com.ngallazzi.githubstargazers.network.GitHub;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nicola on 2017-03-04.
 */

public class GetReposTask{
    final String TAG = GetStarGazersTask.class.getSimpleName();
    private String mOwner;
    private ReposTaskCallbacks mCallbacks;

    public GetReposTask(String owner, ReposTaskCallbacks callbacks) {
        this.mOwner = owner;
        this.mCallbacks = callbacks;
    }

    public void execute(){
        mCallbacks.onStarted();
        GitHub service = new MyHttpClient().retrofit.create(GitHub.class);
        Call<ArrayList<Repo>> call = service.getRepos(mOwner);
        call.enqueue(new Callback<ArrayList<Repo>>() {
            @Override
            public void onResponse(Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {
                if (response.isSuccessful()){
                    mCallbacks.onSuccess(response.body());
                }else {
                    mCallbacks.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Repo>> call, Throwable t) {
                mCallbacks.onError(t.getMessage());
            }
        });
    }

    public interface ReposTaskCallbacks{
        void onStarted();
        void onSuccess(ArrayList<Repo> repos);
        void onError(String error);
    }
}
