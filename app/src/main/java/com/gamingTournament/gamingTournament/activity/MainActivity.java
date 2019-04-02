package com.gamingTournament.gamingTournament.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.gamingTournament.gamingTournament.R;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
        private ImageView image;
        private int n=0;
        private DrawerLayout drawer;
        private ActionBarDrawerToggle mToggle;
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

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        assert getActionBar()!=null;
        getActionBar().setDisplayHomeAsUpEnabled(true);


       }

       public void clicklistener(Class<? extends Activity> c)
       {
           Intent intent =new Intent(MainActivity.this,c);
           startActivity(intent);
       }


    @Override
    public void onClick(View v) {

            n = v.getId();
        switch (n)
        {
            case R.id.pubg_logo : clicklistener(PubgActivity.class);
                                  break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case android.R.id.home : drawer.openDrawer(Gravity.LEFT);
            break;

            case R.id.about_us :
                break;

            case R.id.contact_us :
                break;

            case R.id.privacy_policy :
                break;

            case R.id.terms_conditions:
                break;

            case R.id.refund_policy :
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
