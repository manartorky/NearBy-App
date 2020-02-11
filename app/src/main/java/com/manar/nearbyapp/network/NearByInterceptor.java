package com.manar.nearbyapp.network;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NearByInterceptor implements Interceptor {

    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String VERSION_DATE = "v";

    private String clientId;
    private String clientSecret;

    public NearByInterceptor(String clientSecret, String clientId) {
        this.clientSecret = clientSecret;
        this.clientId = clientId;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String versionDate = getDate();
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(CLIENT_SECRET, clientSecret)
                .addQueryParameter(CLIENT_ID, clientId)
                .addQueryParameter(VERSION_DATE, versionDate)
                .build();

        Request.Builder requestBuilder = original.newBuilder().url(url);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    private String getDate() {
        return new SimpleDateFormat("yyyMMDD", Locale.getDefault()).format(new Date());
    }

}
