
package com.manar.nearbyapp.venues.model.photo;

import com.google.gson.annotations.Expose;


public class PhotosResponse {

    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
