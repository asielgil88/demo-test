package com.demo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.R;
import com.demo.fragments.PreviousSearchFragment;
import com.demo.fragments.SearchFragment;
import com.demo.helper.MyTwitterApiClient;
import com.demo.model.DataManager;
import com.demo.model.SearchModel;
import com.demo.model.UserModel;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class NavigationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final long TIME_INTERVAL = 60 * 1000;
    private static final int QUERY_LIMIT = 10;
    private int fragment_selected = 0;
    private Fragment currentFragment;
    private SearchFragment searchFragment;

    DrawerLayout drawer;
    NavigationView navigationView;

    EditText searchTerm;
    String searchQuery = null;

    Handler handler;

    List<UserModel> users;
    MyTwitterApiClient apiClient;

    private java.lang.Runnable runnable = new Runnable() {

        @Override
        public void run() {
            refreshSearch(false);
            handler.postDelayed(runnable, TIME_INTERVAL);
        }
    };
    private boolean runnableRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchTerm = (EditText) findViewById(R.id.search_term);
        searchTerm.clearFocus();
        handler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayFragment();
        hideSoftKeyboard();

        apiClient = new MyTwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession());

        setUserData();
    }

    private void setUserData() {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if (twitterSession != null) {
            View hView = navigationView.getHeaderView(0);
            TextView userName = (TextView) hView.findViewById(R.id.user_name);
            ImageView userPic = (ImageView) hView.findViewById(R.id.user_pic);
            userName.setText(twitterSession.getUserName());
        }
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchQuery != null)
            handler.postDelayed(runnable, TIME_INTERVAL);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            hideSoftKeyboard();
            fragment_selected = 0;
            displayFragment();
            searchQuery = searchTerm.getText().toString();
            refreshSearch(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshSearch(final boolean persist) {

        if (searchQuery != null && currentFragment instanceof SearchFragment) {

            Call<List<User>> call = apiClient.getCustomService().search(searchQuery, QUERY_LIMIT);
            showLoadingDialog();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void success(Result<List<User>> result) {
                    hideLoadingDialog();
                    processData(persist, result.data, searchQuery);
                    searchFragment.updateUI(users);
                }

                @Override
                public void failure(TwitterException exception) {
                    hideLoadingDialog();
                    Toast.makeText(NavigationActivity.this, "error " + exception.getLocalizedMessage(), Toast.LENGTH_LONG);
                }
            });
        }

        if (!runnableRunning) {
            runnableRunning = true;
            initSearchTask();
        }
    }

    private void initSearchTask() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, TIME_INTERVAL);
    }

    public void displayFragment() {
        Fragment fragment = null;

        switch (fragment_selected) {

            case 0:
                searchFragment = new SearchFragment();
                fragment = searchFragment;
                break;
            case 1:
                fragment = new PreviousSearchFragment();
                break;
            default:
                fragment = new SearchFragment();
                break;

        }

        currentFragment = fragment;
        getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navigation_container, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);

    }

    private void processData(boolean save, List<User> data, String searchQuery) {

        SearchModel searchModel = new SearchModel(searchQuery);
        users = new ArrayList<>();

        if (save) {
            DataManager.saveItem(searchModel);
        }
        for (User u : data) {
            UserModel userModel = new UserModel(searchModel, u);
            if (save) {
                DataManager.saveItem(userModel);
            }
            users.add(userModel);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {
            fragment_selected = 0;
        } else if (id == R.id.nav_manage) {
            fragment_selected = 1;
        }
        displayFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
