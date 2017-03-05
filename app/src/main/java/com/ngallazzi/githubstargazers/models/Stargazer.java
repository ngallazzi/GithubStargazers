package com.ngallazzi.githubstargazers.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-03-02.
 */

public class Stargazer implements Parcelable {
    @SerializedName("login")
    public String login;
    @SerializedName("id")
    public int id;
    @SerializedName("avatar_url")
    public String avatarUrl;

    private Stargazer(Parcel in) {
        login = in.readString();
        id = in.readInt();
        avatarUrl = in.readString();
    }

    public static final Parcelable.Creator<Stargazer> CREATOR
            = new Parcelable.Creator<Stargazer>() {
        public Stargazer createFromParcel(Parcel in) {
            return new Stargazer(in);
        }

        public Stargazer[] newArray(int size) {
            return new Stargazer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(login);
        out.writeInt(id);
        out.writeString(avatarUrl);
    }
}
