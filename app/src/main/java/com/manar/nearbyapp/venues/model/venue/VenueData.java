package com.manar.nearbyapp.venues.model.venue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VenueData {
    @SerializedName("groups")
    @Expose
    private List<VenuesGroups> groupsList;

    public List<VenuesGroups> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(List<VenuesGroups> groupsList) {
        this.groupsList = groupsList;
    }
}
