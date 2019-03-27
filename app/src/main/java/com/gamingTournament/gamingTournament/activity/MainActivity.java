package com.gamingTournament.gamingTournament.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ongoing:
                    Util.changeFragment(MainActivity.this, new OngoingFragment());
                    return true;
                case R.id.navigation_play:
                    Util.changeFragment(MainActivity.this, new PlayFragment());
                    return true;
                case R.id.navigation_me:
                    Util.changeFragment(MainActivity.this, new MeFragment());
                    return true;
                case R.id.navigation_result:
                    Util.changeFragment(MainActivity.this, new ResultFragment());
                    return true;

                           }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean agreed = sharedPreferences.getBoolean("agreed",false);
        if (!agreed) {
            new AlertDialog.Builder(this)
                    .setTitle("Terms and Conditions")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("agreed", true);
                            editor.commit();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            moveTaskToBack(true);
                        }
                    })
                    .setMessage(R.string.TnC)
                    .show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
