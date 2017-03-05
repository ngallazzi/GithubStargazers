package com.ngallazzi.githubstargazers;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.ngallazzi.githubstargazers.adapters.StargazerAdapter;
import com.ngallazzi.githubstargazers.models.Stargazer;
import com.ngallazzi.githubstargazers.network.tasks.GetStarGazersTask;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nicola on 2017-03-05.
 */

public class DisplayResultsActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.dbpLoading)
    DotProgressBar dbpLoading;
    @BindView(R.id.rvStarGazers)
    RecyclerView rvStarGazers;
    @BindView(R.id.tvNoStargazers)
    TextView tvNoStargazers;
    StargazerAdapter mAdapter;
    ArrayList<Stargazer> mStargazersArrayList;
    GetStarGazersTask.StarGazersTaskCallbacks mStarGazersTaskCallbacks;
    String mOwner,mRepository;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int mPageIndex = 1;
    private int mTotalPages = 1;
    private final String SAVED_RECYCLER_VIEW_STATUS_ID = "rv_status_id";
    Parcelable mListState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        ButterKnife.bind(this);
        mContext = this;
        mOwner = getIntent().getStringExtra(getString(R.string.owner_id));
        mRepository = getIntent().getStringExtra(getString(R.string.repository_id));
        setTitle(mOwner + " - " + mRepository);
        initRvStarGazers();
        initStarGazersTaskCallbacks();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetStarGazersTask(mOwner,mRepository,mStarGazersTaskCallbacks).execute(mPageIndex);
    }

    public void initStarGazersTaskCallbacks(){
        mStarGazersTaskCallbacks = new GetStarGazersTask.StarGazersTaskCallbacks() {
            @Override
            public void onStarted() {
                dbpLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<Stargazer> foundStargazers, int totalPages) {
                dbpLoading.setVisibility(View.GONE);
                mTotalPages = totalPages;
                if (foundStargazers.size()==0){
                    tvNoStargazers.setVisibility(View.VISIBLE);
                    rvStarGazers.setVisibility(View.GONE);
                }else {
                    for (Stargazer s: foundStargazers){
                        mStargazersArrayList.add(s);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (mListState!=null){
                        rvStarGazers.getLayoutManager().onRestoreInstanceState(mListState);
                        Log.v(TAG,"Restoring recycler view state");
                    }
                    tvNoStargazers.setVisibility(View.GONE);
                    rvStarGazers.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                dbpLoading.setVisibility(View.GONE);
                Toast.makeText(mContext,error,Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void initRvStarGazers(){
        mStargazersArrayList = new ArrayList<>();
        rvStarGazers.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this);
        rvStarGazers.setLayoutManager(linearLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new StargazerAdapter(mStargazersArrayList,mContext);
        rvStarGazers.setAdapter(mAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (page<=mTotalPages){
                    new GetStarGazersTask(mOwner,mRepository,mStarGazersTaskCallbacks).execute(page);
                    Log.v(TAG,"Page num: " + page);
                    Log.v(TAG,"Total items count " + totalItemsCount);
                }
            }
        };
        // Adds the scroll listener to RecyclerView
        rvStarGazers.addOnScrollListener(scrollListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable listState = rvStarGazers.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVED_RECYCLER_VIEW_STATUS_ID, listState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            mListState = savedInstanceState.getParcelable(SAVED_RECYCLER_VIEW_STATUS_ID);
        }
    }
}
