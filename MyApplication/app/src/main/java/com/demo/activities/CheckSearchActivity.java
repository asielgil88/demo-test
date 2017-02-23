package com.demo.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.demo.R;
import com.demo.fragments.SearchFragment;
import com.demo.model.DataManager;
import com.demo.model.SearchModel;

/**
 * Created by asielgil on 2/23/17.
 */

public class CheckSearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
        long searchid = getIntent().getLongExtra("searchId", -1);

        if(searchid != -1){
            final SearchModel searchModel = (SearchModel) DataManager.getItem(SearchModel.class, searchid);
            toolbar.setTitle("Search Results for " + searchModel.getSearchQuery());

            Bundle bundle = new Bundle();
            bundle.putLong("searchId", searchid);
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.navigation_container, searchFragment)
                    .commit();

        }

    }

}
