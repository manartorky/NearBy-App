package com.manar.nearbyapp.venues.model.photo;

import com.google.gson.annotations.Expose;


public class Response {

    @Expose
    private Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

}
