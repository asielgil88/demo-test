package com.demo.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by asielgil on 2/22/17.
 */

public class DataManager {

    public static List<SearchModel> getAll() {
        return new Select()
                .from(SearchModel.class)
                .orderBy("dateCreated DESC")
                .execute();
    }

    public static Model getItem(Class Klazz, long id){
        return new Select()
                .from(Klazz)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static void saveItem(Model model){
        ActiveAndroid.beginTransaction();
        try {
            model.save();
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

}
