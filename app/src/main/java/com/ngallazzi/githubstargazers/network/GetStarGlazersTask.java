package com.ngallazzi.githubstargazers.network;

import android.content.Context;

import com.ngallazzi.githubstargazers.models.Stargazer;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nicola on 2017-03-02.
 */

public class GetStarGlazersTask {
    final String TAG = GetStarGlazersTask.class.getSimpleName();
    private Context mContext;
    private String mOwner,mRepository;
    private StarGlazersTaskCallbacks mCallbacks;

    public GetStarGlazersTask(Context context, String owner, String repository) {
        this.mContext = context;
        this.mOwner = owner;
        this.mRepository = repository;
        this.mCallbacks = (StarGlazersTaskCallbacks) mContext;
    }

    public void execute(){
        mCallbacks.onStarted();
        ApiService service = new MyHttpClient().sRetrofit.create(ApiService.class);
        Call<ArrayList<Stargazer>> call = service.getStargazers(mOwner,mRepository);
        call.enqueue(new Callback<ArrayList<Stargazer>>() {
            @Override
            public void onResponse(Call<ArrayList<Stargazer>> call, Response<ArrayList<Stargazer>> response) {
                if (response.isSuccessful()){
                    mCallbacks.onSuccess(response.body());
                }else {
                    mCallbacks.onError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Stargazer>> call, Throwable t) {
                mCallbacks.onError();
            }
        });
    }

    public interface StarGlazersTaskCallbacks{
        void onStarted();
        void onSuccess(ArrayList<Stargazer> stargazers);
        void onError();
    }
}
