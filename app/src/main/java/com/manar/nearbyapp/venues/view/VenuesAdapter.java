package com.manar.nearbyapp.venues.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.manar.nearbyapp.R;
import com.manar.nearbyapp.databinding.ItemVenueBinding;
import com.manar.nearbyapp.venues.model.venue.Venue;

import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenueViewHolder> {

    private List<Venue> venueList;


    VenuesAdapter(List<Venue> items) {
        venueList = items;
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVenueBinding venueBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_venue, parent, false);
        return new VenueViewHolder(venueBinding);
    }

    @Override
    public void onBindViewHolder(final VenueViewHolder holder, int position) {
        Venue mItem = venueList.get(position);
        holder.venueBinding.setVenue(mItem);
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    void updateList(List<Venue> venueListWithPhotos) {
        this.venueList = venueListWithPhotos;
        notifyDataSetChanged();
    }

    class VenueViewHolder extends RecyclerView.ViewHolder {
        private ItemVenueBinding venueBinding;

        VenueViewHolder(ItemVenueBinding binding) {
            super(binding.getRoot());
            this.venueBinding = binding;
        }
    }
}
