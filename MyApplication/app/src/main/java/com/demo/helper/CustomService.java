package com.demo.helper;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TwitterCollection;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.Timeline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by asielgil on 2/22/17.
 */

public interface CustomService {
    @GET("/1.1/users/search.json")
    Call<List<User>> search(@Query("q") String q, @Query("count") int count);

    @GET("/1.1/statuses/user_timeline.json")
    Call<List<Tweet>> timeline(@Query("user_id") long user_id);
}
