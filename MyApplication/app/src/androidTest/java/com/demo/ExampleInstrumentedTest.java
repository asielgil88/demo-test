package com.demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.ActiveAndroid;
import com.demo.model.DataManager;
import com.demo.model.SearchModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.demo", appContext.getPackageName());
    }

    @Test
    public void databaseInsert() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ActiveAndroid.initialize(appContext);

        SearchModel searchModel = new SearchModel();
        searchModel.setSearchQuery("Query Test");
        searchModel.setDateCreated(new Date());
        DataManager.saveItem(searchModel);

        if(searchModel.getId() != null){
            SearchModel searchFetched = (SearchModel) DataManager.getItem(SearchModel.class, searchModel.getId());

            assertEquals(searchModel.getId(), searchFetched.getId());
            assertEquals(searchModel.getSearchQuery(), searchFetched.getSearchQuery());
        }

    }
}
