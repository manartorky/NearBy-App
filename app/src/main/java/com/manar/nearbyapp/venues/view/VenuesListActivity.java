package com.manar.nearbyapp.venues.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.manar.nearbyapp.BuildConfig;
import com.manar.nearbyapp.R;
import com.manar.nearbyapp.databinding.ActivityVenuesListBinding;
import com.manar.nearbyapp.utils.LocationManager;
import com.manar.nearbyapp.utils.LocationManagerInteraction;
import com.manar.nearbyapp.utils.PermissionManager;
import com.manar.nearbyapp.venues.model.venue.Venue;
import com.manar.nearbyapp.venues.viewmodel.VenuesViewModel;

import java.lang.annotation.Retention;
import java.util.List;

import io.reactivex.disposables.Disposable;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;
import static com.manar.nearbyapp.utils.LocationManager.REQUEST_CHECK_SETTINGS;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class VenuesListActivity extends AppCompatActivity implements LocationManagerInteraction {

    private static final String TAG = VenuesListActivity.class.getSimpleName();
    private ActivityVenuesListBinding binding;
    private LocationManager locationManager;
    private VenuesViewModel venuesViewModel;
    private Disposable disposable;
    private VenuesAdapter venueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_venues_list);
        setSupportActionBar(binding.toolbar);

        venuesViewModel = new ViewModelProvider(this).get(VenuesViewModel.class);
        initLocationManager();
        setUpdateTypeCheck();
        observeVenuesChanges();
    }

    private void setUpdateTypeCheck() {
        binding.realTimeSwitch.setChecked(venuesViewModel.isRealtimeUpdate());
        binding.realTimeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this,getString(R.string.msg_realtime_enabled),Toast.LENGTH_SHORT).show();
                startLocationUpdates();
            } else {
                Toast.makeText(this,getString(R.string.msg_realtime_disabled),Toast.LENGTH_SHORT).show();
                stopLocationUpdates();
            }
            venuesViewModel.setRealTimeUpdate(isChecked);
        });

    }


    private void observeVenuesChanges() {
        venuesViewModel.observeVenusesChanges().observe(this, listDataWrapper -> {
            switch (listDataWrapper.status) {
                case SUCCESS:
                    hideLoading();
                    if (listDataWrapper.data == null || listDataWrapper.data.isEmpty()) {
                        showErrorMsg(getString(R.string.empty_list_msg));
                    } else {
                        setupRecyclerView(listDataWrapper.data);
                        loadVenueImages(listDataWrapper.data);
                    }
                    break;
                case ERROR:
                    hideLoading();
                    showErrorMsg(getString(R.string.api_error));
                    break;
                case LOADING:
                    showLoading();
                    break;
            }
        });
    }

    private void loadVenueImages(List<Venue> data) {
        disposable = venuesViewModel.getVenuesListWithPhotos(data).subscribe(venueListWithPhotos -> {
            venueAdapter.updateList(venueListWithPhotos);
        }, error -> Log.e(TAG, error.getLocalizedMessage()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void showLoading() {
        binding.errorLayout.setVisibility(View.GONE);
        binding.venuesRecyclerView.setVisibility(View.GONE);
        binding.loadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        binding.venuesRecyclerView.setVisibility(View.VISIBLE);
        binding.loadingLayout.setVisibility(View.GONE);
    }

    private void showErrorMsg(String errorMsg) {
        binding.venuesRecyclerView.setVisibility(View.GONE);
        binding.errorLayout.setVisibility(View.VISIBLE);
        binding.errorTextView.setText(errorMsg);
    }

    private void checkForLocationPermission() {
        PermissionManager.checkForPermission(this, ACCESS_FINE_LOCATION, PermissionManager.LOCATION_PERMISSION_REQUEST_CODE);
    }


    private void launchSettingsScreen() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void showSnackBar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                LENGTH_LONG)
                .setAction(getString(actionStringId), listener).show();
    }

    @SuppressLint("MissingPermission")
    private void initLocationManager() {
        if (isLocationPermissionGranted() || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            locationManager = new LocationManager(this, this);
            startLocationUpdates();
        } else {
            checkForLocationPermission();
        }
    }


    @Override
    public void onLocationRetrieved(Location location) {
        if (!venuesViewModel.isRealtimeUpdate()) {
            stopLocationUpdates();
        }
        venuesViewModel.setCurrentLocation(location);
        Log.e(TAG, location.getLatitude() + "," + location.getLongitude());
    }


    private void startLocationUpdates() {
        if (locationManager != null) {
            locationManager.startLocationUpdates();
        }
    }

    private void stopLocationUpdates() {
        if (locationManager != null) {
            locationManager.stopLocationUpdates();
        }
    }


    private boolean isLocationPermissionGranted() {
        return PermissionManager.isPermissionGranted(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void setupRecyclerView(@NonNull List<Venue> venues) {
        venueAdapter = new VenuesAdapter(venues);
        binding.venuesRecyclerView.setAdapter(venueAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.d(TAG, "User agreed to make required location settings changes.");
                    binding.tryAgainLayout.setVisibility(View.GONE);
                    startLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    Log.d(TAG, "User chose not to make required location settings changes.");
                    showTryAgainLayout(getString(R.string.msg_enable_gps), TRY_AGAIN_GPS);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionManager.LOCATION_PERMISSION_REQUEST_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted.
                initLocationManager();
                binding.tryAgainLayout.setVisibility(View.GONE);
            } else {
                // permission denied
                showSnackBar(R.string.permission_denied_explanation,
                        R.string.settings, view -> {
                            // Build intent that displays the App settings screen.
                            launchSettingsScreen();
                        });

                showTryAgainLayout(getString(R.string.permission_denied_explanation), TRY_AGAIN_PERMISSION);
            }
        }
    }

    @Retention(SOURCE)
    @StringDef({
            TRY_AGAIN_PERMISSION,
            TRY_AGAIN_GPS,
    })
    @interface ActionName {
    }

    public static final String TRY_AGAIN_PERMISSION = "permission";
    public static final String TRY_AGAIN_GPS = "GPS";


    private void showTryAgainLayout(String message, @ActionName String actionName) {
        binding.tryAgainLayout.setVisibility(View.VISIBLE);
        binding.tryAgainTextView.setText(message);
        binding.tryAgainButton.setOnClickListener(v -> {
            if (actionName.equals(TRY_AGAIN_PERMISSION)) {
                initLocationManager();
            } else {
                startLocationUpdates();
            }
        });
    }


}
