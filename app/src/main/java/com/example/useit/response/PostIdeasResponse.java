package com.example.useit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostIdeasResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private Integer username;
    @SerializedName("table_name")
    @Expose
    private Integer table_name;
    @SerializedName("thing")
    @Expose
    private String thing;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Integer getTable_name() {
        return table_name;
    }

    public void setTable_name(Integer table_name) {
        this.table_name = table_name;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }
}

