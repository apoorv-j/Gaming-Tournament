package com.gamingTournament.gamingTournament.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Adapter.ParticipantsAdapter;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.Lists.list_match_results;
import com.gamingTournament.gamingTournament.Lists.list_participants;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.Lists.list_room_details;
import com.gamingTournament.gamingTournament.SharedPrefManager;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.ViewModels.FreefirePlayViewModel;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.FreefireResultViewModel;
import com.gamingTournament.gamingTournament.ViewModels.ParticipantsViewModel;
import com.gamingTournament.gamingTournament.fragment.FreefirePlayFragment;
import com.gamingTournament.gamingTournament.fragment.WalletFragment;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreefireMatchDetailActivity extends FragmentActivity {
    String posString;
    int position;
    private List<list_play> matchDetails;
    TextView matchTitle,dateTime,killPrize,winPrize,entryFee,mode,type,map,playersJoined,joinBtn;
    int matchID,players;
    ParticipantsAdapter adapter;
    RecyclerView recyclerView;
    private AlertDialog.Builder rBuilder,mBuilder,sBuilder,Builder;
    private AlertDialog rDialog,mDialog,sDialog,dialog;
    private Users user;
    ApiInterface apiInterface;
    list_play item;
    String playerNames;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freefire_match_detail);
        final Intent intent = getIntent();
        posString= intent.getStringExtra("position");
        position= Integer.parseInt(posString);
        user = SharedPrefManager.getInstance(this).getUser();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        mBuilder = new AlertDialog.Builder(this);
        rBuilder = new AlertDialog.Builder(this);
        Builder = new AlertDialog.Builder(this);
        sBuilder=new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Its loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        FreefirePlayViewModel model = ViewModelProviders.of(this).get(FreefirePlayViewModel.class);
        model.freefireMatchDetails(this).observe(this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(List<list_play> list_plays) {
                matchDetails= list_plays;
                item = matchDetails.get(position);

                String dateTime1 = item.getDateTime();
                DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH for hour of the day (0 - 23)
                Date d = null;
                try {
                    d = f1.parse(dateTime1);
                    DateFormat f2 = new SimpleDateFormat("yyyy-MM-dd  hh:mma");
                    dateTime1 = f2.format(d).toLowerCase(); // "12:18am"
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Call<List<list_room_details>> call = apiInterface.freefireRoom("PB_PUBG",item.getMatchID(),user.getUname());
                final String finalDateTime = dateTime1;
                call.enqueue(new Callback<List<list_room_details>>() {
                    @Override
                    public void onResponse(Call<List<list_room_details>> call, Response<List<list_room_details>> response) {
                        String roomID =response.body().get(0).getRoomID();
                        String roomPass =response.body().get(0).getRoomPass();
                        String status=response.body().get(0).getStatus();
                        if(status!=null&&!status.equals("not_registered")) {
                            roomDialog(finalDateTime, roomID, roomPass);
                        }

                        else if(roomID!=null&&!roomID.equals(""))
                        {
                            roomDialog(finalDateTime,roomID,roomPass);
                        }
                        else
                        {
                killPrize = findViewById(R.id.fd_textView27);
                winPrize = findViewById(R.id.fd_textView26);
                entryFee = findViewById(R.id.fd_textView25);
                type = findViewById(R.id.fd_textView19);
                mode = findViewById(R.id.fd_textView20);
                map = findViewById(R.id.fd_textView21);
                matchTitle = findViewById(R.id.fd_textView18);
                dateTime = findViewById(R.id.fd_textView23);
                joinBtn =findViewById(R.id.fd_joinMatchDetails);

                matchTitle.setText(item.getMatchTitle());
                dateTime.setText("Match Schedule "+ finalDateTime);
                winPrize.setText("Winning Price: ₹"+item.getWinPrize());
                killPrize.setText("Per Kill: ₹ "+item.getKillPrize());
                entryFee.setText("Entry Fee: ₹ "+item.getEntryFee());
                mode.setText("Version: "+item.getMode());
                map.setText("Map: "+item.getMap());
                type.setText("Type: "+item.getTeamSize());

                int maxPlayers=Integer.parseInt(item.getMaxPlayers());
                int playersJoined=Integer.parseInt(item.getPlayerJoined());

                if(maxPlayers<=playersJoined)
                {
                    joinBtn.setText("MATCH FULL");
                    joinBtn.setBackgroundColor(Color.parseColor("#616161"));
                    joinBtn.setClickable(false);
                }
                else
                {
                    joinBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            joinClickMatchDetails();
                        }
                    });
                }

                matchID=Integer.parseInt(item.getMatchID());
                players=Integer.parseInt(item.getPlayerJoined());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<list_room_details>> call, Throwable t) {
                        Log.e("onFailure: ", t.toString());
                    }
                });


            }
        });
    }


    public void refreshParticipants(View view) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView =findViewById(R.id.fd_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.e("Refresh ","refreshParticipants: clicked");

        ViewModel playerList = ViewModelProviders.of(this).get(ParticipantsViewModel.class);
        ((ParticipantsViewModel) playerList).getDetails(this,item.getMatchID(),"freefire").observe(this, new Observer<List<list_participants>>() {
            @Override
            public void onChanged(List<list_participants> list_participantsList) {
                list_participants item = list_participantsList.get(0);
                String status = item.getStatus();
                if(status==null)
                {
                    adapter=new ParticipantsAdapter(list_participantsList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    public void joinClickMatchDetails() {
        View view = getLayoutInflater().inflate(R.layout.dialog_check_balance, null);
        TextView cancelBTN, nextBTN, tvMessage, matchFee, currBal;
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
                    Util.changeDrawerFragment(FreefireMatchDetailActivity.this, new WalletFragment());
                }
            });
        }

        else
        {
            nextBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                                    switch (item.getTeamSize()) {
                                        case "1": //solo
                                            View ignView = getLayoutInflater().inflate(R.layout.dialog_ign_solo, null);
                                            final EditText player = ignView.findViewById(R.id.ign_edit_dialog_solo);
                                            TextView next;
                                            next = ignView.findViewById(R.id.next_ign_dialog_solo);
                                            mBuilder.setView(ignView);
                                            mDialog = mBuilder.create();
                                            mDialog.show();


                                            next.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    playerNames = player.getText().toString();
                                                    showSuccessDialog(item,user.getUname(),playerNames);

                                                }
                                            });

                                            break;

                                        case "2": //duo
                                            View ignView2 = getLayoutInflater().inflate(R.layout.dialog_ign_duo, null);
                                            final EditText player1 = ignView2.findViewById(R.id.player1_duo);
                                            final EditText player2 = ignView2.findViewById(R.id.player2_duo);

                                            TextView next2;
                                            next2 = ignView2.findViewById(R.id.next_ign_dialog_duo);
                                            mBuilder.setView(ignView2);
                                            mDialog = mBuilder.create();
                                            mDialog.show();


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
                                                        showSuccessDialog(item,user.getUname(),playerNames);
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

                                            TextView next3;

                                            next3 = ignView3.findViewById(R.id.next_ign_dialog_squad);


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
                                                        showSuccessDialog(item,user.getUname(),playerNames);
                                                    }



                                                }
                                            });

                                            break;


                                    }
                                }
                            });
                        }
    }

    private void showSuccessDialog(final list_play item, final String username, final String playerNames)
    {
        mDialog.dismiss();
        progressDialog.show();

        Call<ResponseBody> call = apiInterface.changeBalance("PB_PUBG",username,"sub",item.getEntryFee());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                try {
                    String result=response.body().string();
                    if (result.equals("success")) {
                        Call<ResponseBody> call2 = apiInterface.addFreefirePlayers("PB_PUBG",username,item.getTeamSize(),item.getMatchID(),playerNames);
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
                                                startActivity(new Intent(FreefireMatchDetailActivity.this, MainActivity.class));
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
                        Toast.makeText(FreefireMatchDetailActivity.this, "Add Money in Wallet", Toast.LENGTH_LONG).show();
                        Util.changeDrawerFragment(FreefireMatchDetailActivity.this, new WalletFragment());
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

    private void roomDialog(final String dateTime, final String ID, final String pass) {

        View roomView = getLayoutInflater().inflate(R.layout.dialog_room_details, null);
        final TextView roomID, roomPass, play, share;
        roomID = roomView.findViewById(R.id.roomID);
        roomPass = roomView.findViewById(R.id.roomPass);
        play = roomView.findViewById(R.id.cancel_room_dialog);
        share = roomView.findViewById(R.id.share_room_dialog);

        roomID.setText(ID);
        roomPass.setText(pass);

        rBuilder.setView(roomView);
        rDialog = rBuilder.create();
        rDialog.show();
        rDialog.setCanceledOnTouchOutside(false);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = getPackageManager().getLaunchIntentForPackage("com.dts.freefireth");
                if (localIntent != null)
                {
                    startActivity(localIntent);
                }
                else
                    Toast.makeText(FreefireMatchDetailActivity.this, "Freefire not installed!", Toast.LENGTH_LONG).show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //share on whatsapp
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Game : Freefire"+"\nMatch Date and time: "+dateTime+"\nRoom ID: "+ID+"\n Room Password: "+pass);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Share via"));
            }

        });
    }
}
