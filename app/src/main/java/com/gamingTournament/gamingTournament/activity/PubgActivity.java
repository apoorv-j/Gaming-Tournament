package com.gamingTournament.gamingTournament.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.fragment.MeFragment;
import com.gamingTournament.gamingTournament.fragment.OngoingFragment;
import com.gamingTournament.gamingTournament.fragment.PlayFragment;
import com.gamingTournament.gamingTournament.fragment.ResultFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

public class PubgActivity extends FragmentActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ongoing:
                    Util.changeFragment(PubgActivity.this, new OngoingFragment());
                    return true;
                case R.id.navigation_play:
                    Util.changeFragment(PubgActivity.this, new PlayFragment());
                    return true;
                case R.id.navigation_me:
                    Util.changeFragment(PubgActivity.this, new MeFragment());
                    return true;
                case R.id.navigation_result:
                    Util.changeFragment(PubgActivity.this, new ResultFragment());
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubg);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
