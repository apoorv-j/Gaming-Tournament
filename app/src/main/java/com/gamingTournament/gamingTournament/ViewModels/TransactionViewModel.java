package com.gamingTournament.gamingTournament.ViewModels;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.list_transactions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionViewModel extends ViewModel {
    private String salt= "GT397PB";
    //this is the data that will fetch asynchronously
    private MutableLiveData<List<list_transactions>> transactionList;

    ProgressDialog progressDialog;

    public LiveData<List<list_transactions>> getTransactions(Context context, String uname){

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Just a sec....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(transactionList==null)
        {
            transactionList = new MutableLiveData<>();
            loadEvents(uname);
        }
        return transactionList;
    }

    private void loadEvents(String uname) {

        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<list_transactions>> call = apiInterface.getTransactionHistory(salt,uname);
        call.enqueue(new Callback<List<list_transactions>>() {
            @Override
            public void onResponse(Call<List<list_transactions>> call, Response<List<list_transactions>> response) {
                transactionList.setValue(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<list_transactions>> call, Throwable t) {

            }
        });

    }
}

