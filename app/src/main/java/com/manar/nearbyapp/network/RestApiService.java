package com.manar.nearbyapp.network;


import androidx.lifecycle.LiveData;

import com.manar.nearbyapp.venues.model.venue.VenuesResponse;
import com.manar.nearbyapp.venues.model.photo.PhotosResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApiService {

    @GET("venues/explore")
    LiveData<ApiResponse<VenuesResponse>> getNearByPlaces(@Query("ll") String longLat, @Query("radius") double radius, @Query("limit") int limit);

    @GET("venues/{venueId}/photos")
    Observable<PhotosResponse> getVenuePhotos(@Path("venueId") String id);


}
