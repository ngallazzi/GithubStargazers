package com.ngallazzi.githubstargazers.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-03-04.
 */

public class Repo {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
}
