package com.ngallazzi.githubstargazers.network.tasks;

import android.content.Context;

import com.ngallazzi.githubstargazers.models.Stargazer;
import com.ngallazzi.githubstargazers.network.GitHub;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nicola on 2017-03-02.
 */

public class GetStarGazersTask {
    final String TAG = GetStarGazersTask.class.getSimpleName();
    private String mOwner,mRepository;
    private StarGazersTaskCallbacks mCallbacks;

    public GetStarGazersTask(String owner, String repository, StarGazersTaskCallbacks callbacks) {
        this.mOwner = owner;
        this.mRepository = repository;
        this.mCallbacks = callbacks;
    }

    public void execute(){
        mCallbacks.onStarted();
        GitHub service = new MyHttpClient().retrofit.create(GitHub.class);
        Call<ArrayList<Stargazer>> call = service.getStargazers(mOwner,mRepository);
        call.enqueue(new Callback<ArrayList<Stargazer>>() {
            @Override
            public void onResponse(Call<ArrayList<Stargazer>> call, Response<ArrayList<Stargazer>> response) {
                if (response.isSuccessful()){
                    mCallbacks.onSuccess(response.body());
                }else {
                    mCallbacks.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Stargazer>> call, Throwable t) {
                mCallbacks.onError(t.getMessage());
            }
        });
    }

    public interface StarGazersTaskCallbacks{
        void onStarted();
        void onSuccess(ArrayList<Stargazer> stargazers);
        void onError(String error);
    }
}
