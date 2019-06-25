package com.example.useit.response;

public class TablerosResponse {
    private Integer id;
    private Integer table_owner;
    private String table_name;
    private Integer table_privacy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTable_owner() {
        return table_owner;
    }

    public void setTable_owner(Integer table_owner) {
        this.table_owner = table_owner;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Integer getTable_privacy() {
        return table_privacy;
    }

    public void setTable_privacy(Integer table_privacy) {
        this.table_privacy = table_privacy;
    }
}
