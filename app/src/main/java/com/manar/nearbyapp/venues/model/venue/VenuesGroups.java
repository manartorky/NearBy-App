package com.manar.nearbyapp.venues.model.venue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VenuesGroups {
    @SerializedName("items")
    @Expose
    private List<Item> itemsList;

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }
}
