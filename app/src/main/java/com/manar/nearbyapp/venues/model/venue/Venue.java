package com.manar.nearbyapp.venues.model.venue;


import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manar.nearbyapp.R;
import com.manar.nearbyapp.db.VenueLocationConverter;


@Entity(tableName = "venue")
public class Venue {

    public static final String IMAGE_SIZE = "64";

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    @TypeConverters(VenueLocationConverter.class)
    private VenueLocation venueLocation;
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private String id;
    private String image;

    public Venue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public VenueLocation getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(VenueLocation venueLocation) {
        this.venueLocation = venueLocation;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Glide.with(imageView).load(url)
                    .error(R.drawable.ic_place_holder)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(imageView);
        } else {
            Glide.with(imageView).load(R.drawable.ic_place_holder).into(imageView);
        }
    }

}
