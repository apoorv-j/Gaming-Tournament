package com.gamingTournament.gamingTournament.ViewModels;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.list_events;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsViewModel extends ViewModel {
    private String salt= "GT397PB";
    //this is the data that will fetch asynchronously
    private MutableLiveData<List<list_events>> eventList;

    ProgressDialog progressDialog;

    public LiveData<List<list_events>> getEvents(Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Its loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(eventList==null)
        {
            eventList = new MutableLiveData<>();
            loadEvents();
        }
        return eventList;
    }

    private void loadEvents() {

        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<list_events>> call = apiInterface.getEvent(salt);
        call.enqueue(new Callback<List<list_events>>() {
            @Override
            public void onResponse(Call<List<list_events>> call, Response<List<list_events>> response) {
                eventList.setValue(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<list_events>> call, Throwable t) {

            }
        });

    }
}
