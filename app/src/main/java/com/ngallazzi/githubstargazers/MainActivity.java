package com.ngallazzi.githubstargazers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.ngallazzi.githubstargazers.adapters.StargazerAdapter;
import com.ngallazzi.githubstargazers.models.Repo;
import com.ngallazzi.githubstargazers.models.Stargazer;
import com.ngallazzi.githubstargazers.network.tasks.GetReposTask;
import com.ngallazzi.githubstargazers.network.tasks.GetStarGazersTask;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.etOwner)
    EditText etOwner;
    @BindView(R.id.actvRepositories)
    AutoCompleteTextView actvRepositories;
    @BindView(R.id.btSearch)
    Button btSearch;
    String mOwner,mRepository;
    @BindView(R.id.dbpLoading)
    DotProgressBar dbpLoading;
    GetReposTask.ReposTaskCallbacks mReposTaskCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        initReposTaskCallbacks();
        initBtSearch();
        initEtOwner();
        initActvRepositories();
    }

    public void initReposTaskCallbacks(){
        mReposTaskCallbacks = new GetReposTask.ReposTaskCallbacks() {
            @Override
            public void onStarted() {
                dbpLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<Repo> repos) {
                dbpLoading.setVisibility(View.GONE);
                ArrayList<String> reposNames = new ArrayList<>();
                for (Repo r: repos){
                    reposNames.add(r.name);
                    Log.v(TAG,"repository added: " + r.name);
                }
                ArrayAdapter<String> reposAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,reposNames);
                actvRepositories.setAdapter(reposAdapter);
            }

            @Override
            public void onError(String error) {
                dbpLoading.setVisibility(View.GONE);
                Toast.makeText(mContext,error,Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void initEtOwner(){
        etOwner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOwner = s.toString().replace(" ","");
                Log.v(TAG,"Owner: " + mOwner);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etOwner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    new GetReposTask(mOwner,mReposTaskCallbacks).execute();
                }
            }
        });
    }

    public void initActvRepositories(){
        actvRepositories.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    actvRepositories.showDropDown();
                }
            }
        });
        actvRepositories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actvRepositories.showDropDown();
            }
        });
    }



    public void initBtSearch(){
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepository = actvRepositories.getText().toString();
                if (!mRepository.isEmpty()){
                    Intent intent = new Intent(mContext,DisplayResultsActivity.class);
                    intent.putExtra(getString(R.string.owner_id),mOwner);
                    intent.putExtra(getString(R.string.repository_id),mRepository);
                    startActivity(intent);
                }else{
                    Toast.makeText(mContext,getString(R.string.please_select_repository),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.owner_id),mOwner);
        outState.putString(getString(R.string.repository_id),mRepository);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            mOwner = savedInstanceState.getString(getString(R.string.owner_id));
            mRepository = savedInstanceState.getString(getString(R.string.repository_id));
        }
    }
}
