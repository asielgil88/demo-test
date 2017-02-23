package com.demo.helper;

import com.demo.activities.StartActivity;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by asielgil on 2/23/17.
 */

public class StartActivityTest extends StartActivity {

    private LoginTestCallback mCallback;

    public void setLoginCallback(LoginTestCallback loginCallback){
        mCallback = loginCallback;
    }

    public interface LoginTestCallback{
        void onHandleResponseCalled(TwitterSession session);
    }

    @Override
    public void onHandleAuth(TwitterSession session) {
        mCallback.onHandleResponseCalled(session);
        super.onHandleAuth(session);
    }

}
