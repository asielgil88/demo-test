package com.demo.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.R;
import com.demo.model.UserModel;
import com.demo.utils.AppImageUtil;

import java.util.List;

/**
 * Created by asielgil on 2/22/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> feedItemList;
    private Activity mContext;

    public UserAdapter(Activity context, List<UserModel> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, null);

        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder customViewHolder, int i) {
        UserModel feedItem = feedItemList.get(i);
        customViewHolder.name.setText(feedItem.getName());
        AppImageUtil.displayImageRounded(feedItem.getProfilePicUrl(), customViewHolder.profilePic);

        if(feedItem.getLastTweet() != null){
            customViewHolder.tweetText.setText(feedItem.getLastTweet().getText());
            if(feedItem.getLastTweet().getPicUrl() != null){
                AppImageUtil.loadImage(feedItem.getLastTweet().getPicUrl(), customViewHolder.tweetPic);
                customViewHolder.tweetPic.setVisibility(View.VISIBLE);
            }else{
                customViewHolder.tweetPic.setVisibility(View.GONE);
            }
            customViewHolder.tweetText.setVisibility(View.VISIBLE);
        }else{
            customViewHolder.tweetText.setVisibility(View.GONE);
            customViewHolder.tweetPic.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected ImageView profilePic;

        protected TextView tweetText;
        protected ImageView tweetPic;

        public UserViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.name);
            this.profilePic = (ImageView) view.findViewById(R.id.profile_pic);
            this.tweetText = (TextView) view.findViewById(R.id.tweet_text);
            this.tweetPic = (ImageView) view.findViewById(R.id.tweet_image);
        }
    }

}


