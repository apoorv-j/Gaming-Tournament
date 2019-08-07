package com.gamingTournament.gamingTournament.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.R;

import java.io.IOException;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordResetActivity extends AppCompatActivity implements View.OnClickListener {
    private String salt = "GT397PB";
    EditText editTextusername,editTextOTP,editTextPassword;
    ScrollView scrollView;
    LinearLayout linearLayout,linearOTP,linearPass;
    TextView ResetBtn,goBack;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        scrollView=findViewById(R.id.scrollViewpr);
        linearLayout=findViewById(R.id.linearLayoutpr);
        linearOTP=findViewById(R.id.linearOTP);
        linearPass=findViewById(R.id.linearPass);
        editTextusername=findViewById(R.id.passResetUsername);
        editTextOTP=findViewById(R.id.otpNumber);
        editTextPassword=findViewById(R.id.PasswordReset);
        ResetBtn=findViewById(R.id.passResetBtn);
        goBack=findViewById(R.id.tvGoBack);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        linearPass.setVisibility(View.GONE);
        linearOTP.setVisibility(View.GONE);

        ResetBtn.setOnClickListener(this);
        goBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passResetBtn:
                Random random = new Random();
                final String otp = String.valueOf(random.nextInt(8999)+1000);
                final String username=editTextusername.getText().toString();
                Log.e("OTP:",otp );
                Call<ResponseBody> call = apiInterface.sendOTP(salt,username,otp);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String s =response.body().string();
                            if(s.equals("wrong_username"))
                            {
                                editTextusername.setError("User not Registered");
                                editTextusername.requestFocus();
                                return;
                            }
                            else if(s.equals("sent_successfully"))
                            {
                                editTextusername.setEnabled(false);
                                linearOTP.setVisibility(View.VISIBLE);
                                ResetBtn.setText("NEXT");
                                ResetBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(otp.equals(editTextOTP.getText().toString()))
                                        {
                                            linearOTP.setVisibility(View.GONE);
                                            linearPass.setVisibility(View.VISIBLE);
                                            ResetBtn.setText("CHANGE PASSWORD");
                                            ResetBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Call<ResponseBody> call1 = apiInterface.resetPass(salt,username,editTextPassword.getText().toString());
                                                    call1.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                            try {
                                                                String result = response.body().string();
                                                                if(result.equals("updated_successfully"))
                                                                {
                                                                    Toast.makeText(PasswordResetActivity.this, "Password Updated!", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                    Toast.makeText(PasswordResetActivity.this, result, Toast.LENGTH_LONG).show();
                                                                gotologin();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        else
                                        {   editTextOTP.setError("Incorrect Otp");
                                            editTextusername.requestFocus();
                                        }

                                    }
                                });
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;

            case R.id.tvGoBack :gotologin();
                                break;
        }


    }

    private void gotologin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
