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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ParticipantsViewModel extends ViewModel {

    private String salt= "PB_PUBG";
    //this is the data that will fetch asynchronously
    private MutableLiveData<List<list_participants>> participantsList;

    ProgressDialog progressDoalog;

    //call this method to get data
    public LiveData<List<list_participants>> getDetails(Context context,String matchID,String game){


        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(participantsList == null)
        {
            Log.e(TAG, "matchDetails: log");
            participantsList = new MutableLiveData<>();
            loadDetails(matchID, game);
        }

        return participantsList;

    }

    private void loadDetails(String matchID,String game) {

        // show it
        progressDoalog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<list_participants>> call = apiInterface.getParticipants(salt,matchID,game);
        call.enqueue(new Callback<List<list_participants>>() {
            @Override
            public void onResponse(@NonNull Call<List<list_participants>> call, @NonNull Response<List<list_participants>> response) {
                participantsList.setValue(response.body());
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<list_participants>> call, @NonNull Throwable t) {
                progressDoalog.dismiss();
                Log.e(TAG, "onFailure: log"+t);
            }
        });

    }
}
