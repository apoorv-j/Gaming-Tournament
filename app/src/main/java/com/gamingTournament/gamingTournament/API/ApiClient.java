package com.gamingTournament.gamingTournament.API;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class ApiClient {

    private static  final String BASE_URL = "http://db.purplebytesolutions.com/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {

        Log.e(TAG, "getApiClient: log");

        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        }

        return retrofit;
    }

}
