package com.demo.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.twitter.Extractor;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetEntities;

/**
 * Created by asielgil on 2/22/17.
 */

@Table(name = "Tweet")
public class TweetModel extends Model{

    @Column(name = "text")
    String text;
    @Column(name = "picUrl")
    String picUrl;

    public TweetModel(){
        super();
    }

    public TweetModel(Tweet tweetData){
        text = tweetData.text;

        TweetEntities entities = tweetData.entities;

        MediaEntity found = null;

        if(entities != null && entities.media != null && entities.media.size() > 0){
            for(MediaEntity mediaEntity :entities.media){
                if(mediaEntity.type.equals("photo")){
                    found = mediaEntity;
                    break;
                }
            }
        }

        if(found != null){
            picUrl = found.mediaUrl;
        }

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
