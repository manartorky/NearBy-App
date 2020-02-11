package com.manar.nearbyapp.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.manar.nearbyapp.venues.model.venue.VenueLocation;

public class VenueLocationConverter {

    @TypeConverter
    public String fromVenueObject(VenueLocation venueLocation) {
        if (venueLocation == null) {
            return (null);
        }
        Gson gson = new Gson();
        return gson.toJson(venueLocation);
    }

    @TypeConverter
    public VenueLocation toVenueObject(String venueLocation) {
        if (venueLocation == null) {
            return (null);
        }
        Gson gson = new Gson();
        return gson.fromJson(venueLocation,VenueLocation.class);
    }
}
