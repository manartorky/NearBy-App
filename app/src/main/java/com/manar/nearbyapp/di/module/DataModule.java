package com.manar.nearbyapp.di.module;

import android.app.Application;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.manar.nearbyapp.BuildConfig;
import com.manar.nearbyapp.NearByApplication;
import com.manar.nearbyapp.db.NearByDatabase;
import com.manar.nearbyapp.db.SharedPreferencesManager;
import com.manar.nearbyapp.network.LiveDataCallAdapterFactory;
import com.manar.nearbyapp.network.NearByInterceptor;
import com.manar.nearbyapp.network.RestApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.manar.nearbyapp.BuildConfig.CLIENT_ID;
import static com.manar.nearbyapp.BuildConfig.CLIENT_SECRET;
import static com.manar.nearbyapp.db.SharedPreferencesManager.DEFAULT_APP_PREFS_NAME;


@Module
public class DataModule {
    private static final int CONNECTION_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    private Application application;

    public DataModule(Application application) {
        this.application = application;
    }


    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                //converts Retrofit response into Observable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }

    /**
     * @return A configured {@link OkHttpClient} that will be used for executing network requests
     */
    @Provides
    @Singleton
    OkHttpClient providesHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // add request time out
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        // Add logging into
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(logging);
        // add interceptor with client id and secret
        builder.interceptors().add(new NearByInterceptor(CLIENT_SECRET, CLIENT_ID));
        return builder.build();
    }


    @Provides
    @Singleton
    RestApiService provideRestService(Retrofit retrofit) {
        return retrofit.create(RestApiService.class);
    }

    @Provides
    @Singleton
    NearByDatabase providesDatabase() {
        return NearByDatabase.getDatabaseInstance(application);
    }

    @Provides
    @Singleton
    SharedPreferencesManager providesSharedPrefManager() {
        return new SharedPreferencesManager(NearByApplication.getInstance().getSharedPreferences(DEFAULT_APP_PREFS_NAME, Context.MODE_PRIVATE));
    }


}
