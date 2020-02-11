package com.manar.nearbyapp.venues.di.component;


import com.manar.nearbyapp.di.FragmentScoped;
import com.manar.nearbyapp.di.component.DataComponent;
import com.manar.nearbyapp.venues.di.module.VenuesModule;
import com.manar.nearbyapp.venues.viewmodel.VenuesViewModel;

import dagger.Component;

@FragmentScoped
@Component(dependencies = DataComponent.class,modules = {VenuesModule.class})
public interface VenuesComponent {
    void inject(VenuesViewModel venuesViewModel);
}
