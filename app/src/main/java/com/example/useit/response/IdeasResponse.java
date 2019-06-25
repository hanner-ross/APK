package com.example.useit.response;

public class IdeasResponse {
    private Integer id;
    private Integer user_name;
    private Integer table_name;
    private String thing;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_name() {
        return user_name;
    }

    public void setUser_name(Integer user_name) {
        this.user_name = user_name;
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
