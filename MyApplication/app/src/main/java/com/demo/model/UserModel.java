package com.demo.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by asielgil on 2/22/17.
 */

@Table(name = "User")
public class UserModel extends Model{

    @Column(name = "twitterId")
    private long twitterId;
    @Column(name = "name")
    private String name;
    @Column(name = "profilePicUrl")
    private String profilePicUrl;
    @Column(name = "lastTweet")
    private TweetModel lastTweet;
    @Column(name = "search")
    private SearchModel search;

    public UserModel() {
        super();
    }

    public UserModel(SearchModel search, User userData){
        this.search = search;
        this.twitterId = userData.id;
        this.name = userData.screenName;
        this.profilePicUrl = userData.profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public TweetModel getLastTweet() {
        return lastTweet;
    }

    public void setLastTweet(TweetModel lastTweet) {
        this.lastTweet = lastTweet;
    }

    public long getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(long twitterId) {
        this.twitterId = twitterId;
    }
}
