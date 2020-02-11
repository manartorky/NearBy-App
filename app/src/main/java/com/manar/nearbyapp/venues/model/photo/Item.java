package com.manar.nearbyapp.venues.model.photo;

import com.google.gson.annotations.Expose;

public class Item {
    @Expose
    private String id;
    @Expose
    private String prefix;
    @Expose
    private String suffix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
