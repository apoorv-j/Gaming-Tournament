package com.gamingTournament.gamingTournament.ViewModels;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.list_play;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MinimPlayViewModel extends ViewModel {

    private String salt= "GT397PB";
    //this is the data that will fetch asynchronously
    private MutableLiveData<List<list_play>> minimMatchList;

    ProgressDialog progressDoalog;

    //call this method to get data
    public LiveData<List<list_play>> minimMatchDetails(Context context){


        progressDoalog = new ProgressDialog(context);

        progressDoalog.setMessage("Its loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(minimMatchList == null)
        {
            Log.e(TAG, "minimMatchDetails: log");
            minimMatchList = new MutableLiveData<>();
            loadDetails();
        }

        return minimMatchList;

    }

    private void loadDetails() {

        // show it
        progressDoalog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<list_play>> call = apiInterface.matchDetailsMinim(salt);
        call.enqueue(new Callback<List<list_play>>() {
            @Override
            public void onResponse(@NonNull Call<List<list_play>> call, @NonNull Response<List<list_play>> response) {
                Log.e(TAG, "onResponse: log");
                minimMatchList.setValue(response.body());
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<list_play>> call, @NonNull Throwable t) {
                progressDoalog.dismiss();
                Log.e(TAG, "onFailure: log"+t);
            }
        });

    }
}
