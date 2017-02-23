package com.demo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.demo.R;
import com.demo.activities.BaseActivity;
import com.demo.adapters.UserAdapter;
import com.demo.helper.MyTwitterApiClient;
import com.demo.model.DataManager;
import com.demo.model.TweetModel;
import com.demo.model.UserModel;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asielgil on 2/22/17.
 */

public class SearchFragment extends Fragment {

    RecyclerView recycler;
    List<UserModel> users = new ArrayList<>();
    private BaseActivity activity;

    private MyTwitterApiClient apiClient;
    UserAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();

        apiClient = new MyTwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);

        return view;
    }

    public void updateUI(List<UserModel> usersFetched) {
        this.users = usersFetched;
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UserAdapter(getActivity(), users);
        recycler.setAdapter(adapter);

        fetchAditionalData();
    }

    public void fetchAditionalData() {

        for (int i= 0; i < users.size(); i ++) {

            final UserModel u = users.get(i);

            final int finalI = i;

            apiClient.getCustomService().timeline(u.getTwitterId()).enqueue(new Callback<List<Tweet>>() {
                @Override
                public void success(Result<List<Tweet>> result) {

                    if (result.data.size() > 0) {
                        Tweet lastTweet = result.data.get(0);
                        TweetModel tweetModel = new TweetModel(lastTweet);
                        u.setLastTweet(tweetModel);
                        DataManager.saveItem(tweetModel);
                        u.save();
                        adapter.notifyItemChanged(finalI);
                    }

                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(getActivity(), "error " + exception.getLocalizedMessage(), Toast.LENGTH_LONG);
                }
            });

        }

    }

}
