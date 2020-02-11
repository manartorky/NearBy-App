package com.manar.nearbyapp.venues.viewmodel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.manar.nearbyapp.venues.model.DataWrapper;
import com.manar.nearbyapp.NearByApplication;
import com.manar.nearbyapp.db.SharedPreferencesManager;
import com.manar.nearbyapp.venues.di.component.DaggerVenuesComponent;
import com.manar.nearbyapp.venues.di.module.VenuesModule;
import com.manar.nearbyapp.venues.model.venue.Venue;
import com.manar.nearbyapp.venues.repository.VenueRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

import static com.manar.nearbyapp.db.SharedPreferencesManager.PREF_IS_REALTIME;


public class VenuesViewModel extends ViewModel {

    private static final double RADIUS = 1000;
    @Inject
    VenueRepository repository;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    private MutableLiveData<String> currentLocationLiveData = new MutableLiveData<>();
    private LiveData<DataWrapper<List<Venue>>> venuesListWrapperLiveData;
    private Location currentLocation;

    public VenuesViewModel() {
        DaggerVenuesComponent.builder()
                .dataComponent(NearByApplication.getInstance().getDataComponent())
                .venuesModule(new VenuesModule())
                .build()
                .inject(this);

        venuesListWrapperLiveData = Transformations.switchMap(currentLocationLiveData, latLong -> repository.getVenues(latLong, RADIUS));
    }

    public LiveData<DataWrapper<List<Venue>>> observeVenusesChanges() {
        return venuesListWrapperLiveData;
    }

    public void setCurrentLocation(Location location) {
        //
        if (currentLocation == null || currentLocation.distanceTo(location) > 500) {
            currentLocation = location;
            String latLong = String.format("%s,%s", location.getLatitude(), location.getLongitude());
            currentLocationLiveData.setValue(latLong);
        }
    }


    public void setRealTimeUpdate(boolean isRealTime) {
        sharedPreferencesManager.saveBoolean(PREF_IS_REALTIME, isRealTime);
    }

    /**
     * this method returns Venue list after adding photos to every relevant venue
     *
     * @param venueList venue list without images
     * @return venueList with images.
     */
    public Single<List<Venue>> getVenuesListWithPhotos(List<Venue> venueList) {
        return repository.getVenuePhotos(venueList).map(imagesMap -> {
            for (Venue venue : venueList) {
                venue.setImage(imagesMap.get(venue.getId()));
            }
            return venueList;
        });
    }

    public boolean isRealtimeUpdate() {
        return sharedPreferencesManager.getBoolean(PREF_IS_REALTIME);
    }
}
