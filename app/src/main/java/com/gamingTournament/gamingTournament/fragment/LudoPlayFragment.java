package com.gamingTournament.gamingTournament.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Adapter.LudoPlayAdapter;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.SharedPrefManager;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.ViewModels.LudoPlayViewModel;
import com.gamingTournament.gamingTournament.activity.LudoMatchDetailActivity;
import com.gamingTournament.gamingTournament.activity.MainActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LudoPlayFragment extends Fragment implements LudoPlayAdapter.OnItemClickListener {
    private String salt = "GT397PB";
    public LudoPlayFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    LudoPlayAdapter adapter;
    List<list_play> ludoMatchDetails;
    String playerNames;
    private AlertDialog.Builder mBuilder, rBuilder, Builder;
    private AlertDialog mDialog, rDialog,dialog;
    String result;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        mBuilder = new AlertDialog.Builder(getActivity());
        rBuilder = new AlertDialog.Builder(getActivity());
        Builder = new AlertDialog.Builder(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Its loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        LudoPlayViewModel model = ViewModelProviders.of(LudoPlayFragment.this).get(LudoPlayViewModel.class);

        model.ludoMatchDetails(getContext()).observe(LudoPlayFragment.this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(@NonNull List<list_play> list_plays) {
                String status = list_plays.get(0).getStatus();
                if(status == null) {
                    ludoMatchDetails = list_plays;
                    adapter = new LudoPlayAdapter(LudoPlayFragment.this, list_plays);
                    recyclerView.setAdapter(adapter);
                }
            }
        }); return view;
    }


    @Override
    public void onAdapterClick(int position) {
        Intent intent = new Intent(getActivity(), LudoMatchDetailActivity.class);
        intent.putExtra("position",String.valueOf(position));
        startActivity(intent);
    }

    @Override
    public void onJoinClick(int position) {
        final list_play item = ludoMatchDetails.get(position);
        final Users user = SharedPrefManager.getInstance(getContext()).getUser();

        View view = getLayoutInflater().inflate(R.layout.dialog_check_balance, null);
        TextView cancelBTN, nextBTN, tvMessage,currBal,matchFee;
        cancelBTN = view.findViewById(R.id.cancel_check_balance);
        nextBTN = view.findViewById(R.id.next_check_balance);
        tvMessage=view.findViewById(R.id.message_check_balance);
        matchFee=view.findViewById(R.id.matchFee);
        currBal=view.findViewById(R.id.currBalance);
        currBal.setText("₹"+user.getBalance());
        matchFee.setText("₹"+item.getEntryFee());
        Builder.setView(view);
        dialog = Builder.create();
        dialog.show();
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if(Integer.parseInt(user.getBalance())<Integer.parseInt(item.getEntryFee()))
        {
            tvMessage.setText("You don't have enough balance in wallet to join this match.");
            tvMessage.setTextColor(Color.parseColor("#d50000"));
            nextBTN.setText("Add Balance");
            nextBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Util.changeDrawerFragment(getActivity(), new WalletFragment());
                }
            });
        }

        else
        {
            nextBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressDialog.show();

                                View ignView = getLayoutInflater().inflate(R.layout.dialog_ign_solo, null);
                                final EditText player = ignView.findViewById(R.id.ign_edit_dialog_solo);
                    TextView next,title,cancel;
                    cancel = ignView.findViewById(R.id.cancel_ign_dialog_solo);
                    next = ignView.findViewById(R.id.next_ign_dialog_solo);
                    title = ignView.findViewById(R.id.title_ign_dialog_solo);
                    title.setText("ENTER LUDO USERNAMES");
                    mBuilder.setView(ignView);
                    mDialog = mBuilder.create();
                    mDialog.show();

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (player.getText().toString().isEmpty()) {
                                            player.setError("Enter Player");
                                            player.requestFocus();
                                        } else {
                                            playerNames = player.getText().toString();
                                            showSuccessDialog(item, user.getUname(), playerNames);
                                        }
                                    }
                                });
                }
            });
        }
    }

    private void showSuccessDialog(final list_play item, final String username, final String playerNames)
    {
        mDialog.dismiss();
        progressDialog.show();

        Call<ResponseBody> call = apiInterface.changeBalance(salt,username,"sub",item.getEntryFee());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                try {
                    String result=response.body().string();
                    if (result.equals("success")) {
                        Call<ResponseBody> call2 = apiInterface.addLudoPlayers(salt,username,item.getMatchID(),playerNames);
                        call2.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String s = response.body().string();
                                    if(s.equals("player_added_successfully"))
                                    {
                                        final View successView = getLayoutInflater().inflate(R.layout.dialog_disclaimer, null);
                                        final TextView home,successTitle;
                                        home = successView.findViewById(R.id.disclaimer_home_btn);
                                        successTitle=successView.findViewById(R.id.disclamer_title);
                                        successTitle.setText("Match #"+item.getMatchID());
                                        rBuilder.setView(successView);
                                        rDialog = rBuilder.create();
                                        rDialog.show();
                                        progressDialog.dismiss();

                                        home.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(getActivity(), MainActivity.class));
                                            }
                                        });
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressDialog.dismiss();
                            }
                        });
                    }

                    else if(result.equals("low_balance"))
                    {   progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Add Money in Wallet", Toast.LENGTH_LONG).show();
                        Util.changeDrawerFragment(getActivity(), new WalletFragment());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

}