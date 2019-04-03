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
import android.widget.TextView;
import android.widget.Toolbar;

import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.fragment.AboutUsFragment;
import com.gamingTournament.gamingTournament.fragment.ContactUsFragment;
import com.gamingTournament.gamingTournament.fragment.FreefireFragment;
import com.gamingTournament.gamingTournament.fragment.LudoFragment;
import com.gamingTournament.gamingTournament.fragment.MinimFragment;
import com.gamingTournament.gamingTournament.fragment.PrivacyPolicyFragment;
import com.gamingTournament.gamingTournament.fragment.PubgFragment;
import com.gamingTournament.gamingTournament.fragment.RefundFragment;
import com.gamingTournament.gamingTournament.fragment.tncFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
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
                    .setMessage(R.string.privacy_text)
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

            NavigationView navigationView = findViewById(R.id.side_nav_bar);
            navigationView.setNavigationItemSelectedListener(this);

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
            case R.id.pubg_logo : Util.changeDrawerFragment(MainActivity.this, new PubgFragment());
                                  break;
            case R.id.freefire_logo : Util.changeDrawerFragment(MainActivity.this, new FreefireFragment());
                break;

            case R.id.ludo_logo : Util.changeDrawerFragment(MainActivity.this, new LudoFragment());
                break;

            case R.id.minim_logo : Util.changeDrawerFragment(MainActivity.this, new MinimFragment());
                break;

            case R.id.textView15 : clicklistener(PaymentPage.class);
                                    break;
            case R.id.textView150 : clicklistener(PaymentPage.class);
                break;
            case R.id.textView151 : clicklistener(PaymentPage.class);
                break;
            case R.id.textView152 : clicklistener(PaymentPage.class);
                break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case android.R.id.home : drawer.openDrawer(Gravity.LEFT);
            break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.home: clicklistener(MainActivity.class);
                    break;

                case R.id.about_us: Util.changeDrawerFragment(MainActivity.this, new AboutUsFragment());
                    drawer.closeDrawer(Gravity.LEFT);
                    break;

                case R.id.contact_us: Util.changeDrawerFragment(MainActivity.this, new ContactUsFragment());
                    drawer.closeDrawer(Gravity.LEFT);
                    break;

                case R.id.privacy_policy:
                    Util.changeDrawerFragment(MainActivity.this, new PrivacyPolicyFragment());
                    drawer.closeDrawer(Gravity.LEFT);
                    break;

                case R.id.terms_conditions: Util.changeDrawerFragment(MainActivity.this, new tncFragment());
                    drawer.closeDrawer(Gravity.LEFT);
                    break;

                case R.id.refund_policy: Util.changeDrawerFragment(MainActivity.this, new RefundFragment());
                    drawer.closeDrawer(Gravity.LEFT);
                    break;
            }
            return true;
    }
}
