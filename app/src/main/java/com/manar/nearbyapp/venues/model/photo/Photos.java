
package com.manar.nearbyapp.venues.model.photo;

import com.google.gson.annotations.Expose;

import java.util.List;


public class Photos {

    @Expose
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
