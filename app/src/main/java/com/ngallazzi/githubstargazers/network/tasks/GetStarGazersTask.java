package com.ngallazzi.githubstargazers.network.tasks;

import android.content.Context;

import com.ngallazzi.githubstargazers.models.Stargazer;
import com.ngallazzi.githubstargazers.network.GitHub;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.internal.http2.Header;
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

    public void execute(int page){
        mCallbacks.onStarted();
        GitHub service = new MyHttpClient().retrofit.create(GitHub.class);
        Call<ArrayList<Stargazer>> call = service.getStargazers(mOwner,mRepository,page);
        call.enqueue(new Callback<ArrayList<Stargazer>>() {
            @Override
            public void onResponse(Call<ArrayList<Stargazer>> call, Response<ArrayList<Stargazer>> response) {
                if (response.isSuccessful()){
                    Headers headers = response.headers();
                    int totalPages = getTotalPages(headers);
                    mCallbacks.onSuccess(response.body(),totalPages);
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

    public int getTotalPages(Headers headers){
        int pageCount = 1;
        String link = headers.get("Link");
        if (link!=null){
            String [] elements = link.split(";");
            int position = elements[1].lastIndexOf("page=");
            String page = elements[1].substring(position,elements[1].length()-1);
            pageCount = Integer.valueOf(page.replace("page=",""));
        }
        return pageCount;
    }

    public interface StarGazersTaskCallbacks{
        void onStarted();
        void onSuccess(ArrayList<Stargazer> stargazers, int totalPages);
        void onError(String error);
    }
}
