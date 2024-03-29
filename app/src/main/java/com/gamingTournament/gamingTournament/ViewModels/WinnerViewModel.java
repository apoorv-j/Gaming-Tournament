package com.gamingTournament.gamingTournament.ViewModels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.list_winner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class WinnerViewModel extends ViewModel {

    private String salt = "GT397PB";
    //this is the data that will fetch asynchronously
    private MutableLiveData<List<list_winner>> winnerList;

    //call this method to get data
    public LiveData<List<list_winner>> matchWinner(String matchID,String game) {

        if (winnerList == null) {
            Log.e(TAG, "onResponse: match winner ");
            winnerList = new MutableLiveData<>();
            loadDetails(matchID,game);
        }

        return winnerList;

    }

    private void loadDetails(String matchID,String game) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<list_winner>> call = apiInterface.getWinners(salt,matchID,game);
        call.enqueue(new Callback<List<list_winner>>() {
            @Override
            public void onResponse(@NonNull Call<List<list_winner>> call, @NonNull Response<List<list_winner>> response) {
                winnerList.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<list_winner>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: match winner " + t);
            }
        });


    }

}