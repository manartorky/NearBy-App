package com.manar.nearbyapp.venues.model.venue;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenuesResponse {
    @SerializedName("response")
    @Expose
    private VenueData venueData;

    public VenueData getVenueData() {
        return venueData;
    }

    public void setVenueData(VenueData venueData) {
        this.venueData = venueData;
    }
}


