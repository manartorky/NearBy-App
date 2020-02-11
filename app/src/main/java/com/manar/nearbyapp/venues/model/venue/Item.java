package com.manar.nearbyapp.venues.model.venue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("venue")
    @Expose
    private Venue venue;

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
