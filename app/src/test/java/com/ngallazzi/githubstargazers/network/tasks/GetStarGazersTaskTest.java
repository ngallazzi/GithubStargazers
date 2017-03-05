package com.ngallazzi.githubstargazers.network.tasks;

import android.util.Log;

import com.ngallazzi.githubstargazers.models.Repo;
import com.ngallazzi.githubstargazers.models.Stargazer;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Created by Nicola on 2017-03-04.
 */
public class GetStarGazersTaskTest {
    private static final String REPO_USER = "ngallazzi";
    private static final String REPO_NAME = "EnlightingLayout";
    private final CountDownLatch latch = new CountDownLatch(1);
    private ArrayList<Stargazer> mStargazers;

    @Test
    public void testGetStarGazersTask(){
        mStargazers = new ArrayList<>();
        try{
            new GetStarGazersTask(REPO_USER,REPO_NAME, new GetStarGazersTask.StarGazersTaskCallbacks() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onSuccess(ArrayList<Stargazer> stargazers, int page) {
                    mStargazers = stargazers;
                    latch.countDown();
                }

                @Override
                public void onError(String error) {
                    Assert.fail("Error executing task: " + error);
                    latch.countDown();
                }
            }).execute(1);
            latch.await();
            assertNotNull(mStargazers);
        }catch (Exception e){
            Assert.fail("Error executing task: " + e.getMessage());
            latch.countDown();
        }
    }
}