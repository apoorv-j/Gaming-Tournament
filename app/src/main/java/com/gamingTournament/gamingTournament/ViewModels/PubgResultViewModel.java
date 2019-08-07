package com.gamingTournament.gamingTournament.ViewModels;

import android.util.Log;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.list_match_results;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PubgResultViewModel extends ViewModel {

    private String salt= "GT397PB";
    //this is the data that will fetch asynchronously
    private MutableLiveData<List<list_match_results>> resultList;

    //call this method to get data
    public LiveData<List<list_match_results>> matchResults(){

        if(resultList == null)
        {
            Log.e(TAG, "onResponse: match results " );
            resultList = new MutableLiveData<>();
            loadDetails();
        }

        return resultList;

    }

    private void loadDetails() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<list_match_results>> call = apiInterface.matchResults(salt);
        call.enqueue(new Callback<List<list_match_results>>() {
            @Override
            public void onResponse(@NonNull Call<List<list_match_results>> call, @NonNull Response<List<list_match_results>> response) {
                Log.e(TAG, "onResponse: match results "+response );
                resultList.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<list_match_results>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: match results " + t);
            }
        });


    }
}

