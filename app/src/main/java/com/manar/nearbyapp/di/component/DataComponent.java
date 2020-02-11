package com.manar.nearbyapp.di.component;


import com.manar.nearbyapp.NearByApplication;
import com.manar.nearbyapp.db.NearByDatabase;
import com.manar.nearbyapp.db.SharedPreferencesManager;
import com.manar.nearbyapp.di.module.DataModule;
import com.manar.nearbyapp.network.RestApiService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class})
public interface DataComponent {
    RestApiService getRestApiService();

    NearByDatabase getNearByDatabase();

    SharedPreferencesManager getSharePrefManager();

    void inject(NearByApplication nearByApplication);
}
