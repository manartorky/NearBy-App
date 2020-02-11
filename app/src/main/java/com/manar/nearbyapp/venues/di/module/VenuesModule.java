package com.manar.nearbyapp.venues.di.module;

import com.manar.nearbyapp.db.AppExecutors;
import com.manar.nearbyapp.db.NearByDatabase;
import com.manar.nearbyapp.network.RestApiService;
import com.manar.nearbyapp.venues.repository.VenueRepository;

import dagger.Module;
import dagger.Provides;


@Module
public class VenuesModule {

    @Provides
    VenueRepository getVenuesRepository(RestApiService apiService, NearByDatabase nearByDatabase) {
        return new VenueRepository(apiService, nearByDatabase, AppExecutors.getInstance());
    }

}
