package com.manar.nearbyapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.manar.nearbyapp.venues.model.venue.Venue;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface VenuesDao {
    @Insert(onConflict = REPLACE)
    void insertAll(List<Venue> venueList);

    @Query("SELECT * FROM Venue")
    LiveData<List<Venue>> getAllVenues();

    @Query("DELETE FROM Venue")
    void clearAll();
}
