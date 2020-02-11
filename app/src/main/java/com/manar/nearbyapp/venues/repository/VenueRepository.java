package com.manar.nearbyapp.venues.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.manar.nearbyapp.venues.model.DataWrapper;
import com.manar.nearbyapp.db.AppExecutors;
import com.manar.nearbyapp.db.NearByDatabase;
import com.manar.nearbyapp.db.VenuesDao;
import com.manar.nearbyapp.network.ApiResponse;
import com.manar.nearbyapp.network.NetworkBoundResource;
import com.manar.nearbyapp.network.NetworkUtils;
import com.manar.nearbyapp.network.RestApiService;
import com.manar.nearbyapp.venues.model.venue.Item;
import com.manar.nearbyapp.venues.model.venue.Venue;
import com.manar.nearbyapp.venues.model.venue.VenuesGroups;
import com.manar.nearbyapp.venues.model.venue.VenuesResponse;
import com.manar.nearbyapp.venues.model.photo.PhotosResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VenueRepository {

    private static final int ITEMS_LIMIT = 10;
    private final AppExecutors appExecutors;
    private RestApiService apiService;
    private VenuesDao venuesDao;
    private static final String SIZE = "150x150";

    @Inject
    public VenueRepository(RestApiService apiService, NearByDatabase nearByDatabase, AppExecutors appExecutors) {
        this.apiService = apiService;
        this.appExecutors = appExecutors;
        venuesDao = nearByDatabase.getVenueDao();
    }

    public LiveData<DataWrapper<List<Venue>>> getVenues(String latLong, double radius) {
        return new NetworkBoundResource<List<Venue>, VenuesResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull VenuesResponse venuesResponse) {
                // clear old data & save new data to keep cached data always up-to-date
                venuesDao.clearAll();
                venuesDao.insertAll(getVenueList(venuesResponse));
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Venue> data) {
                return data == null || data.isEmpty() || NetworkUtils.isNetworkAvailable();
            }

            @NonNull
            @Override
            protected LiveData<List<Venue>> loadFromDb() {
                return venuesDao.getAllVenues();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<VenuesResponse>> createCall() {
                return apiService.getNearByPlaces(latLong, radius, ITEMS_LIMIT);
            }
        }.getAsLiveData();
    }

    private List<Venue> getVenueList(VenuesResponse venuesResponse) {
        List<Venue> venuesList = new ArrayList<>();
        if (venuesResponse != null && venuesResponse.getVenueData() != null && !venuesResponse.getVenueData().getGroupsList().isEmpty()) {
            for (VenuesGroups venuesGroups : venuesResponse.getVenueData().getGroupsList()) {
                for (Item item : venuesGroups.getItemsList()) {
                    venuesList.add(item.getVenue());
                }
            }
        }
        return venuesList;
    }

    /**
     * This method takes list of venues and creates chain of requests to get their relevant photos.
     * @param venueList list of items that we want to get their photos
     * @return map that contains photos as values and venue's id as key.
     */
    public Single<Map<String, String>> getVenuePhotos(List<Venue> venueList) {
        Map<String, String> imagesList = new HashMap<>();
        return Observable.fromIterable(venueList)
                .flatMap((Function<Venue, ObservableSource<Pair<String, PhotosResponse>>>) venue -> Observable.zip(
                        Observable.just(venue.getId()),
                        apiService.getVenuePhotos(venue.getId()),
                        Pair::new))
                .toList().map(pairs -> {
                    for (Pair<String, PhotosResponse> pair : pairs) {
                        if (pair.first != null && pair.second != null) {
                            String imagePath = String.format("%s%s%s", pair.second.getResponse().getPhotos().getItems().get(0).getPrefix(),
                                    SIZE,
                                    pair.second.getResponse().getPhotos().getItems().get(0).getSuffix());
                            // add venue id as key & and image as value
                            imagesList.put(pair.first, imagePath);
                        }
                    }
                    return imagesList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
