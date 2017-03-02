package com.ngallazzi.githubstargazers.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-03-02.
 */

public class Stargazer {
    @SerializedName("login")
    String login;
    @SerializedName("id")
    int id;
    @SerializedName("avatar_url")
    String avatarUrl;
}
