package com.manar.nearbyapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.manar.nearbyapp.venues.model.venue.Venue;


@Database(entities = {Venue.class}, version = 1, exportSchema = false)
public abstract class NearByDatabase extends RoomDatabase {

    private static final String DATA_BASE_NAME = "nearby_db";
    private static NearByDatabase instance;

    public static synchronized NearByDatabase getDatabaseInstance(Context context) {
        // insure that no other reference is created on different threads.
        if (instance == null) {
            // create our one and only db object.
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    // fallbackToDestructiveMigration is a migration strategy that destroy and re-creating existing db
                    // fallbackToDestructiveMigration is only used for small applications like we are implementing now
                    // for real projects we need to implement non-destructive migration strategy.
                    NearByDatabase.class, DATA_BASE_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract VenuesDao getVenueDao();
}
