package com.gamingTournament.gamingTournament.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.SharedPrefManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private String salt = "GT397PB";
    private AutoCompleteTextView editTextfullname,editTextusername,editTextemail,editTextphNumber;
    private EditText editTextpassword;
    private TextView SignUp,gtSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextfullname=findViewById(R.id.fullName);
        editTextusername=findViewById(R.id.UsernameReg);
        editTextemail=findViewById(R.id.EmailReg);
        editTextphNumber=findViewById(R.id.phNumber);
        editTextpassword=findViewById(R.id.PasswordReg);
        SignUp=findViewById(R.id.btnSignUp);
        gtSignIn=findViewById(R.id.gtSignIn);

        SignUp.setOnClickListener(this);
        gtSignIn.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String name = editTextfullname.getText().toString().trim();
        String phNumber=editTextphNumber.getText().toString().trim();
        String username=editTextusername.getText().toString().trim();

        if (email.isEmpty()) {
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Enter a valid email");
            editTextemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextpassword.setError("Password required");
            editTextpassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextpassword.setError("Password should be atleast 6 character long");
            editTextpassword.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextfullname.setError("Name required");
            editTextfullname.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            editTextusername.setError("Name required");
            editTextusername.requestFocus();
            return;
        }
        if (phNumber.isEmpty() || !android.util.Patterns.PHONE.matcher(phNumber).matches()) {
            editTextphNumber.setError("Enter a valid mobile number");
            editTextphNumber.requestFocus();

        }
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        Call<ResponseBody> call = apiInterface.createUser(salt,username,password,name,phNumber,email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    switch (s)
                    {
                        case "error":
                            Toast.makeText(SignUp.this, "Something went Wrong", Toast.LENGTH_LONG).show();
                            break;
                        case "user_already_exists":
                            Toast.makeText(SignUp.this, "User already EXISTS!", Toast.LENGTH_LONG).show();
                            break;
                        case "added_successfully":
                            Toast.makeText(SignUp.this, "Registered!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUp.this, LoginActivity.class));
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignUp.this, "Cannot Register, Try Again.", Toast.LENGTH_LONG).show();

            }


        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                userSignUp();
                break;
            case R.id.gtSignIn:

                startActivity(new Intent(this, LoginActivity.class));

                break;
        }
    }

}
