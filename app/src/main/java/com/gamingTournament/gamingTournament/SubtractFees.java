package com.gamingTournament.gamingTournament;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.activity.MainActivity;
import com.gamingTournament.gamingTournament.fragment.WalletFragment;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubtractFees {
    private String fees;
    private Users user;
    private final String salt = "PB_PUBG";
    private Context context;
    private FragmentActivity fragmentContext;
    private String result;

    public SubtractFees(Context context, FragmentActivity fragmentContext) {
        this.context = context;
        this.fragmentContext = fragmentContext;
    }

    public String subtract(String fees)
    {
        this.fees=fees;
        user = SharedPrefManager.getInstance(context).getUser();


        final ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.e( "onJoinClick: ","1");

        Call<ResponseBody> call = apiInterface.changeBalance(salt,user.getUname(),"sub",fees);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e( "onJoinClick: ","2");
                try {
                    result=response.body().string();


                    if(result.equals("error"))
                    {
                        Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                    }
                    else if(result.equals("success"))
                    {
                        Call<ResponseBody> call1 = apiInterface.getBalance(salt,user.getUname());
                        call1.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String s= response.body().string();
                                    if(!s.equals("error"))
                                    {
                                        SharedPrefManager.getInstance(context).updateBalance(s);
                                        MainActivity activity = new MainActivity();
                                        activity.setUserBalance(s);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e( "onJoinClick: ",t.toString());
                            }
                        });
                    }

                    else if(result.equals("low_balance"))
                    {
                        Toast.makeText(context, "Add Money in Wallet", Toast.LENGTH_LONG).show();
                        Util.changeDrawerFragment(fragmentContext, new WalletFragment());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return result;
    }
}
