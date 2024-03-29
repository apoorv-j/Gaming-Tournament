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
import com.gamingTournament.gamingTournament.Adapter.PubgPlayAdapter;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.SharedPrefManager;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.ViewModels.PlayViewModel;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.activity.MainActivity;
import com.gamingTournament.gamingTournament.activity.PubgMatchDetailActivity;
import com.gamingTournament.gamingTournament.Lists.list_play;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment implements PubgPlayAdapter.OnItemClickListener {
    private String salt = "GT397PB";
    public PlayFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    PubgPlayAdapter adapter;
    public List<list_play> matchDetails;
    String playerNames;
    private AlertDialog.Builder mBuilder, sBuilder, Builder;
    private AlertDialog mDialog, sDialog,dialog;
    String result;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        mBuilder = new AlertDialog.Builder(getActivity());
        sBuilder = new AlertDialog.Builder(getActivity());
        Builder = new AlertDialog.Builder(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Its loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        PlayViewModel model = ViewModelProviders.of(PlayFragment.this).get(PlayViewModel.class);

        model.matchDetails(getContext()).observe(PlayFragment.this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(@NonNull List<list_play> list_plays) {
                String status = list_plays.get(0).getStatus();
                if(status == null) {
                    matchDetails = list_plays;
                    adapter = new PubgPlayAdapter(PlayFragment.this, list_plays);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        return view;
    }


    @Override
    public void onAdapterClick(int position) {
        Intent intent = new Intent(getActivity(), PubgMatchDetailActivity.class);
        intent.putExtra("position", String.valueOf(position));

        startActivity(intent);
    }

    @Override
    public void onJoinClick(int position) {
        final list_play item = matchDetails.get(position);
        final Users user = SharedPrefManager.getInstance(getContext()).getUser();
        View view = getLayoutInflater().inflate(R.layout.dialog_check_balance, null);
        final TextView cancelBTN, nextBTN, tvMessage, matchFee, currBal;
        currBal=view.findViewById(R.id.currBalance);
        matchFee=view.findViewById(R.id.matchFee);
        cancelBTN = view.findViewById(R.id.cancel_check_balance);
        nextBTN = view.findViewById(R.id.next_check_balance);
        tvMessage=view.findViewById(R.id.message_check_balance);

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
                    int maxPlayers = Integer.parseInt(item.getMaxPlayers());
                    int playersJoined = Integer.parseInt(item.getPlayerJoined());
                    String teamSize = item.getTeamSize();


                    if(teamSize.equals("4")||teamSize.equals("2")) {
                        if (maxPlayers - playersJoined == 1)
                            teamSize = "1";
                        else if ((maxPlayers - playersJoined == 2) || (maxPlayers - playersJoined == 3))
                            teamSize = "2";
                    }

                    switch (teamSize) {
                        case "1": //solo
                            View ignView = getLayoutInflater().inflate(R.layout.dialog_ign_solo, null);
                            final EditText player = ignView.findViewById(R.id.ign_edit_dialog_solo);
                            TextView next,cancel;
                            cancel=ignView.findViewById(R.id.cancel_ign_dialog_solo);
                            next = ignView.findViewById(R.id.next_ign_dialog_solo);
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
                                    playerNames = player.getText().toString();
                                    showSuccessDialog(playerNames,user.getUname(),item);

                                }
                            });

                            break;

                        case "2": //duo
                            View ignView2 = getLayoutInflater().inflate(R.layout.dialog_ign_duo, null);
                            final EditText player1 = ignView2.findViewById(R.id.player1_duo);
                            final EditText player2 = ignView2.findViewById(R.id.player2_duo);

                            TextView next2,cancel2;
                            cancel2=ignView2.findViewById(R.id.cancel_ign_dialog_duo);
                            next2 = ignView2.findViewById(R.id.next_ign_dialog_duo);
                            mBuilder.setView(ignView2);
                            mDialog = mBuilder.create();
                            mDialog.show();

                            cancel2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                }
                            });

                            next2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (player1.getText().toString().isEmpty()) {
                                        player1.setError("Enter Player 1");
                                        player1.requestFocus();
                                    } else {
                                        playerNames = player1.getText().toString();
                                        if (!player2.getText().toString().isEmpty())
                                            playerNames=playerNames.concat("/" + player2.getText().toString());
                                        showSuccessDialog(playerNames,user.getUname(),item);
                                    }


                                }
                            });

                            break;

                        case "4": //squad
                            final View ignView3 = getLayoutInflater().inflate(R.layout.dialog_ign_squad, null);
                            final EditText player1s = ignView3.findViewById(R.id.player1_squad);
                            final EditText player2s = ignView3.findViewById(R.id.player2_squad);
                            final EditText player3s = ignView3.findViewById(R.id.player3_squad);
                            final EditText player4s = ignView3.findViewById(R.id.player4_squad);
                            mBuilder.setView(ignView3);
                            mDialog = mBuilder.create();
                            mDialog.show();

                            TextView next3,cancel3;
                            cancel3 =ignView3.findViewById(R.id.cancel_ign_dialog_squad);
                            next3 = ignView3.findViewById(R.id.next_ign_dialog_squad);

                            cancel3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                }
                            });
                            next3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (player1s.getText().toString().isEmpty()) {
                                        player1s.setError("Enter Player 1");
                                        player1s.requestFocus();
                                    } else {
                                        playerNames = player1s.getText().toString();
                                        if (!player2s.getText().toString().isEmpty())
                                            playerNames =playerNames.concat("/" + player2s.getText().toString());
                                        if (!player3s.getText().toString().isEmpty())
                                            playerNames =playerNames.concat("/" + player3s.getText().toString());
                                        if (!player4s.getText().toString().isEmpty())
                                            playerNames = playerNames.concat("/" + player4s.getText().toString());

                                        showSuccessDialog(playerNames,user.getUname(),item);
                                    }



                                }
                            });

                            break;


                    }
                }
            });
        }
    }

    private void showSuccessDialog(final String playerNames, final String username, final list_play item)
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
                        Call<ResponseBody> call2 = apiInterface.addPubgPlayers(salt,username,item.getTeamSize(),item.getMatchID(),playerNames);
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
                                        sBuilder.setView(successView);
                                        sDialog = sBuilder.create();
                                        sDialog.show();
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