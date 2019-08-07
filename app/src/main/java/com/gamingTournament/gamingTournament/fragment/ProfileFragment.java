package com.gamingTournament.gamingTournament.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.SharedPrefManager;
import com.gamingTournament.gamingTournament.activity.MainActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private String salt = "GT397PB";

    public ProfileFragment() {
        // Required empty public constructor
    }

    AutoCompleteTextView fullname,username,email,phNumber;
    EditText editTextOldPass,editTextNewPass;
    String oldPass,newPass;
    TextView balance,btnReset;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        fullname = view.findViewById(R.id.fullNameProfile);
        username = view.findViewById(R.id.UsernameProfile);
        email = view.findViewById(R.id.EmailProfile);
        phNumber = view.findViewById(R.id.phNumberProfile);
        balance = view.findViewById(R.id.balanceProfile);
        editTextOldPass=view.findViewById(R.id.oldPasswordProfile);
        editTextNewPass=view.findViewById(R.id.newPasswordProfile);
        btnReset=view.findViewById(R.id.btnResetPass);

        final Users user= SharedPrefManager.getInstance(getActivity()).getUser();

        fullname.setText(user.getReal_name());
        fullname.setEnabled(false);
        username.setText(user.getUname());
        username.setEnabled(false);
        email.setText(user.getUemail());
        email.setEnabled(false);
        phNumber.setText(user.getUphone());
        phNumber.setEnabled(false);
        balance.setText("Balance: ₹"+user.getBalance());

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass=editTextOldPass.getText().toString();
                newPass=editTextNewPass.getText().toString();
                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<ResponseBody> call = apiInterface.changePass(salt,user.getUname(),oldPass,newPass);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            switch (result)
                            {
                                case "error":
                                    Toast.makeText(getContext(), "Error! Try again Later", Toast.LENGTH_SHORT).show();
                                    break;

                                case "old_pass_not_matching":
                                    editTextOldPass.setError("Old Password didn't match");
                                    editTextOldPass.requestFocus();
                                    break;

                                case "updated_successfully":
                                    Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                    break;
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
        });

        return view;
    }

}
