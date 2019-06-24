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
import com.gamingTournament.gamingTournament.Lists.list_participants;
import com.gamingTournament.gamingTournament.Lists.list_top_players;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class TopPlayerViewModel extends ViewModel {

    private String salt= "PB_PUBG";
    //this is the data that will fetch asynchronously
    private MutableLiveData<List<list_top_players>> topPlayers;

    ProgressDialog progressDialog;

    //call this method to get data
    public LiveData<List<list_top_players>> getDetails(Context context){


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Its loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(topPlayers == null)
        {
            topPlayers = new MutableLiveData<>();
            loadDetails();
        }

        return topPlayers;

    }

    private void loadDetails() {

        // show it
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<list_top_players>> call = apiInterface.getTopPlayers(salt);
        call.enqueue(new Callback<List<list_top_players>>() {
            @Override
            public void onResponse(Call<List<list_top_players>> call, Response<List<list_top_players>> response) {
                topPlayers.setValue(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<list_top_players>> call, Throwable t) {

            }
        });
    }
}
