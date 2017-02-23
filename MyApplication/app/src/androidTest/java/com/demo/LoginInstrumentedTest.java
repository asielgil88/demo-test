package com.demo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.util.Log;
import com.demo.helper.StartActivityTest;
import com.twitter.sdk.android.core.TwitterSession;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

/**
 * Created by asielgil on 2/23/17.
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {

    @Rule
    public ActivityTestRule<StartActivityTest> mActivityRule = new ActivityTestRule<>(StartActivityTest.class);

    @Test
    public void testTwitterLogin() throws Exception {

        final Object syncObject = new Object();

        StartActivityTest startActivityTest = mActivityRule.getActivity();
        startActivityTest.setLoginCallback(new StartActivityTest.LoginTestCallback() {
            @Override
            public void onHandleResponseCalled(TwitterSession session) {
                Log.v("TAG", "onHandleResponseCalled in thread " + Thread.currentThread().getId());
                assertTrue(session != null);
                synchronized (syncObject){
                    syncObject.notify();
                }
            }
        });

        onView(withId(R.id.twitter_login_button)).perform(click());

        synchronized (syncObject){
            syncObject.wait();
        }
    }
}
