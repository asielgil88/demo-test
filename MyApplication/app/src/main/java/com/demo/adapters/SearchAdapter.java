package com.demo.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.R;
import com.demo.model.SearchModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by asielgil on 2/23/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<SearchModel> feedItemList;
    private Activity mContext;

    public SearchAdapter(Activity context, List<SearchModel> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, null);
        SearchViewHolder viewHolder = new SearchViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder customViewHolder, int i) {
        SearchModel feedItem = feedItemList.get(i);

        customViewHolder.searchQuery.setText(feedItem.getSearchQuery());
        customViewHolder.searchResultCount.setText(feedItem.users().size() + "users Found");
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        customViewHolder.searchDate.setText(format.format(feedItem.getDateCreated()));

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        protected TextView searchQuery;
        protected TextView searchDate;
        protected TextView searchResultCount;

        public SearchViewHolder(View view) {
            super(view);
            this.searchQuery = (TextView) view.findViewById(R.id.search_query);
            this.searchDate = (TextView) view.findViewById(R.id.search_date);
            this.searchResultCount = (TextView) view.findViewById(R.id.search_result_count);
        }
    }

}



