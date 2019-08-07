package com.gamingTournament.gamingTournament.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String salt = "GT397PB";
    private AutoCompleteTextView editTextUsername;
    private EditText editTextPassword;
    TextView SignIn,gtSignUp,forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername=findViewById(R.id.usernamelog);
        editTextPassword=findViewById(R.id.passlog);
        SignIn=findViewById(R.id.btnSignIn);
        gtSignUp=findViewById(R.id.gtsignup);
        forgotPass=findViewById(R.id.tvForgotPass);

        forgotPass.setOnClickListener(this);
        SignIn.setOnClickListener(this);
        gtSignUp.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userLogin() {

        final String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (username.isEmpty()) {
            editTextUsername.setError("Username required");
            editTextUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Users>> call = apiInterface.getUser(username,password,salt);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                String status =response.body().get(0).getStatus();

                if(status!=null) {
                    switch (status) {
                        case "error":
                            Toast.makeText(LoginActivity.this, "Error Logging In, Try Again", Toast.LENGTH_LONG).show();
                            break;
                        case "wrong_pass":
                            editTextPassword.setError("Password is Incorrect");
                            editTextPassword.requestFocus();
                            break;
                        case "no_such_user":
                            editTextUsername.setError("User not exist");
                            editTextUsername.requestFocus();
                            break;
                    }
                }
                else{
                    SharedPrefManager.getInstance(LoginActivity.this)
                            .saveUser(response.body().get(0));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.e( "onFailure: ", t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                userLogin();
                break;
            case R.id.gtsignup:
                startActivity(new Intent(this, SignUp.class));
                break;

            case R.id.tvForgotPass:
                startActivity(new Intent(this, PasswordResetActivity.class));
                break;
        }
    }
}
