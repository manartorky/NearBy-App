package com.manar.nearbyapp.venues.model.venue;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VenueLocation {
    @SerializedName("formattedAddress")
    private
    List<String> formattedAddressList;
    private String address;

    public String getAddress() {
        if (formattedAddressList != null && !formattedAddressList.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String mAddress : formattedAddressList) {
                builder.append(mAddress);
                builder.append(" ");
            }
            return builder.toString();
        } else {
            return address;
        }
    }
}
