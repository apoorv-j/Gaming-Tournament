package com.gamingTournament.gamingTournament.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Adapter.ParticipantsAdapter;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.Lists.list_participants;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.Lists.list_room_details;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.SharedPrefManager;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.ViewModels.MinimPlayViewModel;
import com.gamingTournament.gamingTournament.ViewModels.ParticipantsViewModel;
import com.gamingTournament.gamingTournament.fragment.MinimPlayFragment;
import com.gamingTournament.gamingTournament.fragment.WalletFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinimMatchDetailActivity extends AppCompatActivity {
    private String salt = "GT397PB";
    String posString;
    int position;
    private List<list_play> matchDetails;
    TextView matchTitle,dateTime,winPrize,entryFee,map,joinBtn;
    ImageView imageView;
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
    String URL ="http://gamingtournament.in/appimg/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minim_match_detail);
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
        progressDialog.setMessage("Just a sec...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        ViewModel model = ViewModelProviders.of(this).get(MinimPlayViewModel.class);
        ((MinimPlayViewModel)model).minimMatchDetails(this).observe(this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(List<list_play> list_plays) {
                matchDetails=list_plays;
              item =matchDetails.get(position);
                String dateTime1 = item.getDateTime();
                DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH for hour of the day (0 - 23)
                Date d = null;
                try {
                    d = f1.parse(dateTime1);
                    DateFormat f2 = new SimpleDateFormat("dd-MM-yyyy   hh:mm a");
                    dateTime1 = f2.format(d).toLowerCase(); // "12:18am"
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Call<List<list_room_details>> call = apiInterface.minimRoom(salt,item.getMatchID(),user.getUname());
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
                winPrize = findViewById(R.id.mmd_textView26);
                entryFee = findViewById(R.id.mmd_textView20);
                map = findViewById(R.id.mmd_textView19);
                matchTitle = findViewById(R.id.mmd_textView18);
                dateTime = findViewById(R.id.mmd_textView23);
                joinBtn =findViewById(R.id.mmd_joinMatchDetails);
                imageView=findViewById(R.id.mmd_image);

                            Picasso.get()
                                    .load(URL+"minim.jpg")
                                    .fit()
                                    .into(imageView);

                matchTitle.setText(item.getMatchTitle()+"- Match#"+item.getMatchID());
                dateTime.setText("Match Schedule "+finalDateTime);
                winPrize.setText("Winning Price: ₹"+item.getWinPrize());
                entryFee.setText("Entry Fee: ₹ "+item.getEntryFee());
                map.setText("Map: "+item.getMap());
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

    public void mmdParticipants(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView =findViewById(R.id.mmd_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.e("Refresh ","refreshParticipants: clicked");

        ViewModel playerList = ViewModelProviders.of(this).get(ParticipantsViewModel.class);
        ((ParticipantsViewModel) playerList).getDetails(this,item.getMatchID(),"minimilitia").observe(this, new Observer<List<list_participants>>() {
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
        cancelBTN = view.findViewById(R.id.cancel_check_balance);
        nextBTN = view.findViewById(R.id.next_check_balance);
        tvMessage = view.findViewById(R.id.message_check_balance);
        currBal=view.findViewById(R.id.currBalance);
        matchFee=view.findViewById(R.id.matchFee);
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

        if (Integer.parseInt(user.getBalance()) < Integer.parseInt(item.getEntryFee())) {
            tvMessage.setText("You don't have enough balance in wallet to join this match.");
            tvMessage.setTextColor(Color.parseColor("#d50000"));
            nextBTN.setText("Add Balance");
            nextBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Util.changeDrawerFragment(MinimMatchDetailActivity.this, new WalletFragment());
                }
            });
        } else {
            nextBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                                    View ignView = getLayoutInflater().inflate(R.layout.dialog_ign_solo, null);
                                    final EditText player = ignView.findViewById(R.id.ign_edit_dialog_solo);
                                    TextView next, title;

                                    next = ignView.findViewById(R.id.next_ign_dialog_solo);
                                    title = ignView.findViewById(R.id.title_ign_dialog_solo);
                                    title.setText("ENTER MINI MILITIA USERNAME");
                                    mBuilder.setView(ignView);
                                    mDialog = mBuilder.create();
                                    mDialog.show();


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

    private void showSuccessDialog(final list_play item, final String username, final String playerNames) {
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
                        Call<ResponseBody> call2 = apiInterface.addMinimPlayers(salt,username,item.getMatchID(),playerNames);
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
                                                startActivity(new Intent(MinimMatchDetailActivity.this, MainActivity.class));
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
                        Toast.makeText(MinimMatchDetailActivity.this, "Add Money in Wallet", Toast.LENGTH_LONG).show();
                        Util.changeDrawerFragment(MinimMatchDetailActivity.this, new WalletFragment());
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

      /*  play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = getPackageManager().getLaunchIntentForPackage("");
                if (localIntent != null)
                {
                    startActivity(localIntent);
                }
                else
                    Toast.makeText(MinimMatchDetailActivity.this, "Mini Militia not installed!", Toast.LENGTH_LONG).show();
            }
        });*/

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //share on whatsapp
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Game : Mini Militia"+"\nMatch Date and time: "+dateTime+"\nRoom ID: "+ID+"\n Room Password: "+pass);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Share via"));
            }

        });
    }
}