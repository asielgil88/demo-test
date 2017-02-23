package com.demo.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by asielgil on 2/22/17.
 */

@Table(name = "Search")
public class SearchModel extends Model{

    @Column(name = "searchQuery")
    private String searchQuery;

    @Column(name = "dateCreated")
    private Date dateCreated;

    public List<UserModel> users() {
        return getMany(UserModel.class, "search");
    }

    public SearchModel(){
        super();
    }

    public SearchModel(String q){
        dateCreated = new Date();
        searchQuery = q;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
