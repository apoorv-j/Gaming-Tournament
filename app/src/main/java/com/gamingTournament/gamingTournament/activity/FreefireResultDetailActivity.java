package com.gamingTournament.gamingTournament.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.Adapter.FullResultAdapter;
import com.gamingTournament.gamingTournament.Adapter.WinnerAdapter;
import com.gamingTournament.gamingTournament.Lists.list_match_results;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.Lists.list_winner;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.FreefirePlayViewModel;
import com.gamingTournament.gamingTournament.ViewModels.FreefireResultViewModel;
import com.gamingTournament.gamingTournament.ViewModels.PubgResultViewModel;
import com.gamingTournament.gamingTournament.ViewModels.WinnerViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FreefireResultDetailActivity extends FragmentActivity {
    String posString;
    int position;
    private List<list_play> matchDetails;
    private List<list_match_results> matchResults;
    TextView matchTitle,dateTime,killPrize,winPrize,entryFee;
    RecyclerView wRecyclerView;
    RecyclerView fRecyclerView;
    LinearLayoutManager manager;
    LinearLayoutManager wmanager;
    LinearLayoutManager rmanager;
    WinnerAdapter winnerAdapter;
    FullResultAdapter fullResultAdapter;
    String matchID,perKill,youtubeID;
    ImageView yIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
        final Intent intent = getIntent();
        posString= intent.getStringExtra("position");
        position= Integer.parseInt(posString);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setActionBar(toolbar);
        Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

        manager = new LinearLayoutManager(this);
        wmanager = new LinearLayoutManager(this);
        rmanager = new LinearLayoutManager(this);

        wRecyclerView = findViewById(R.id.w_recycler_view);
        fRecyclerView = findViewById(R.id.fr_recycler_view);
        wRecyclerView.setLayoutManager(wmanager);
        fRecyclerView.setLayoutManager(rmanager);

        ViewModel model = ViewModelProviders.of(this).get(FreefirePlayViewModel.class);
        ((FreefirePlayViewModel) model).freefireMatchDetails(this).observe(this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(List<list_play> list_plays) {
                matchDetails = list_plays;
                list_play item = matchDetails.get(position);

                killPrize = findViewById(R.id.ww_textView7);
                winPrize = findViewById(R.id.ww_textView6);
                entryFee = findViewById(R.id.ww_textView8);
                matchTitle = findViewById(R.id.r_match_title);
                dateTime = findViewById(R.id.r_match_date);
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

                matchTitle.setText(item.getMatchTitle() + "- Match#" + item.getMatchID());
                dateTime.setText("Organised on " + dateTime1);
                winPrize.setText("₹"+ item.getWinPrize());
                killPrize.setText("₹" + item.getKillPrize());
                entryFee.setText("₹" + item.getEntryFee());

                matchID=item.getMatchID();
                perKill=item.getKillPrize();
                youtubeID=item.getYoutube();

                ViewModel winnermodel = ViewModelProviders.of(FreefireResultDetailActivity.this).get(WinnerViewModel.class);
                ((WinnerViewModel) winnermodel).matchWinner(matchID,"freefire").observe(FreefireResultDetailActivity.this, new Observer<List<list_winner>>() {
                    @Override
                    public void onChanged(List<list_winner> list_winners) {
                        Log.e("Winner :","adapter"+matchID);
                        winnerAdapter= new WinnerAdapter(list_winners);
                        wRecyclerView.setAdapter(winnerAdapter);
                    }
                });            }});




        ViewModel resultmodel = ViewModelProviders.of(this).get(FreefireResultViewModel.class);
        ((FreefireResultViewModel) resultmodel).matchResults().observe(this, new Observer<List<list_match_results>>() {
            @Override
            public void onChanged(List<list_match_results> list_match_results) {

                fullResultAdapter = new FullResultAdapter(list_match_results,matchID,perKill);
                fRecyclerView.setAdapter(fullResultAdapter);
            }
        });

    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
