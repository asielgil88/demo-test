package com.demo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.demo.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class StartActivity extends BaseActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    private TwitterLoginButton loginButton;


    Callback<TwitterSession> twitterCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            TwitterSession session = result.data;
            String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            onHandleAuth(session);
        }

        @Override
        public void failure(TwitterException exception) {
            Log.d("TwitterKit", "Login with Twitter failure", exception);
            onHandleAuth(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(twitterCallback);

    }

    protected void onHandleAuth(TwitterSession twitterSession) {
        if (twitterSession != null) {
            Intent intent = new Intent(this, NavigationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
