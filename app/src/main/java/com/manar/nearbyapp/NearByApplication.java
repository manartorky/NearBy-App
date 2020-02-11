package com.manar.nearbyapp;

import android.app.Application;

import com.manar.nearbyapp.di.component.DaggerDataComponent;
import com.manar.nearbyapp.di.component.DataComponent;
import com.manar.nearbyapp.di.module.DataModule;

public class NearByApplication extends Application {
    private static NearByApplication instance;
    DataComponent dataComponent;

    public static NearByApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDataComponent();
    }

    private void initDataComponent() {
        dataComponent = DaggerDataComponent.builder().
                dataModule(new DataModule(this))
                .build();
        dataComponent.inject(this);
    }

    public DataComponent getDataComponent() {
        return dataComponent;
    }
}
