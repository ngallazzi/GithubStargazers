package com.ngallazzi.githubstargazers.network.tasks;

import com.ngallazzi.githubstargazers.models.Repo;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Created by Nicola on 2017-03-04.
 */
public class GetReposTaskTest {
    private static final String REPO_USER = "ngallazzi";
    private final CountDownLatch latch = new CountDownLatch(1);
    private Repo firstRepo;

    @Test
    public void testGetReposTask(){
        try{
            new GetReposTask(REPO_USER, new GetReposTask.ReposTaskCallbacks() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onSuccess(ArrayList<Repo> repos) {
                    if (!repos.isEmpty()) {
                        firstRepo = repos.get(0);
                    }
                    latch.countDown();
                }

                @Override
                public void onError(String error) {
                    latch.countDown();
                    Assert.fail("Error executing task: " + error);
                }
            }).execute();
            latch.await();
            Assert.assertNotNull(firstRepo);
        }catch (Exception e){
            Assert.fail("Error executing task: " + e.getMessage());
            latch.countDown();
        }
    }
}