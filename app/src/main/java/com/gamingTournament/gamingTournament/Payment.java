package com.gamingTournament.gamingTournament;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.API.PaytmApi;
import com.gamingTournament.gamingTournament.Lists.Checksum;
import com.gamingTournament.gamingTournament.Lists.Constants;
import com.gamingTournament.gamingTournament.Lists.Paytm;
import com.gamingTournament.gamingTournament.activity.MainActivity;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class Payment implements PaytmPaymentTransactionCallback {

    private Context context;
    private Paytm paytm;
    private Retrofit retrofit;
    private PaytmApi apiService;
    private String username;
    ProgressDialog progressDoalog;


    public Payment(Context context) {
        this.context= context;

    }

    public void generateCheckSum(String amount, String username) {

       /* //run-time permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }*/
        this.username = username;
        //getting the tax amount first.
        String txnAmount;
        txnAmount = amount;


        //creating a retrofit object.
        retrofit = new Retrofit.Builder()
                .baseUrl(PaytmApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        apiService = retrofit.create(PaytmApi.class);

        //creating paytm object
        //containing all the values required
        paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {

            @Override
            public void onResponse(@NonNull Call<Checksum> call, @NonNull Response<Checksum> response) {

                Log.v(MainActivity.class.getSimpleName(),"response is generated");
                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment(response.body().getChecksumHash(), paytm);
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {
                Log.e(TAG, "onFailure: log"+t);


            }

        });
    }

    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

       /* //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();*/

        //use this when using for production
        PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());

        Log.e(TAG, "checksum: "+ checksumHash);
        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(context, true, true, this);

    }

    //all these overriden method is to detect the payment result accordingly
    @Override
    public void onTransactionResponse(Bundle bundle) {

        String response=bundle.getString("RESPMSG");
        Log.e(TAG, "onTransactionResponse: "+response );
        if (response.equals("Txn Success")) {
            progressDoalog = new ProgressDialog(context);
            progressDoalog.setMessage("Its loading....");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();

            Call<ResponseBody> call = apiService.getStatus(paytm.getOrderId(),paytm.getmId());
            Log.e(TAG, "onTransactionResponse: "+"Txn Success" );
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String result = null;
                    try {
                        result = response.body().string();
                        Log.e(TAG,"Transaction Status : "+result);
                        int index1 = result.indexOf("TXN_SUCCESS");
                        int index2 = result.indexOf("Txn Success");
                        if(index1>0 && index2>0)
                        {
                            Toast.makeText(context, "Transaction Successful!", Toast.LENGTH_SHORT).show();
                            updateBalance(paytm.getTxnAmount());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "initialize: " );

                }
            });
        }
    }

    private void updateBalance(String amount)
    {
        Log.e(TAG, "updateBalance: "+amount );

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.changeBalance("PB_PUBG",username,"add",amount);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    if(s.equals("success"))
                    {
                        clicklistener(MainActivity.class);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void clicklistener(Class<? extends Activity> c)
    {
        Intent intent =new Intent(context,c);
        progressDoalog.dismiss();
        context.startActivity(intent);
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(context, "Back Pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(context, s + bundle.toString(), Toast.LENGTH_LONG).show();
    }
}
