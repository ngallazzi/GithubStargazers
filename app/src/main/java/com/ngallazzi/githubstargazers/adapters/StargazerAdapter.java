package com.ngallazzi.githubstargazers.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngallazzi.githubstargazers.DisplayResultsActivity;
import com.ngallazzi.githubstargazers.MainActivity;
import com.ngallazzi.githubstargazers.R;
import com.ngallazzi.githubstargazers.models.Stargazer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nicola on 2017-03-04.
 */

public class StargazerAdapter extends RecyclerView.Adapter<StargazerAdapter.ViewHolder> {
    private final String TAG = StargazerAdapter.class.getSimpleName();
    private ArrayList<Stargazer> mStargazers;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView civUserAvatar;
        public TextView tvUserName;
        public ViewHolder(View v) {
            super(v);
            civUserAvatar = (CircleImageView) v.findViewById(R.id.civUserAvatar);
            tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        }
    }

    public StargazerAdapter(ArrayList<Stargazer> myDataset, Context context) {
        mStargazers = myDataset;
        mContext = context;
    }

    @Override
    public StargazerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user, parent, false);
        ViewHolder vh = new ViewHolder(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Stargazer stargazer = mStargazers.get(position);
        Picasso.with(mContext).load(stargazer.avatarUrl).into(holder.civUserAvatar);
        holder.tvUserName.setText(stargazer.login);
    }

    @Override
    public int getItemCount() {
        if (mStargazers!=null){
            return mStargazers.size();
        }else{
            return 0;
        }
    }
}
