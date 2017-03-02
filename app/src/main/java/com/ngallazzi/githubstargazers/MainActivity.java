package com.ngallazzi.githubstargazers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.etOwner)
    EditText etOwner;
    @BindView(R.id.actvRepositories)
    AutoCompleteTextView actvRepositories;
    @BindView(R.id.btSearch)
    Button btSearch;
    @BindView(R.id.rvStarGazers)
    RecyclerView rvStarGazers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
