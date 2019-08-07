package com.gamingTournament.gamingTournament.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.API.PaytmApi;
import com.gamingTournament.gamingTournament.Adapter.MainAdapter;
import com.gamingTournament.gamingTournament.BuildConfig;
import com.gamingTournament.gamingTournament.Lists.Paytm;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.Lists.update_details;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.SharedPrefManager;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.fragment.AboutUsFragment;
import com.gamingTournament.gamingTournament.fragment.TopPlayersFragment;
import com.gamingTournament.gamingTournament.fragment.ContactUsFragment;
import com.gamingTournament.gamingTournament.fragment.EventsFragment;
import com.gamingTournament.gamingTournament.fragment.FreefireFragment;
import com.gamingTournament.gamingTournament.fragment.LudoFragment;
import com.gamingTournament.gamingTournament.fragment.MinimFragment;
import com.gamingTournament.gamingTournament.fragment.PrivacyPolicyFragment;
import com.gamingTournament.gamingTournament.fragment.ProfileFragment;
import com.gamingTournament.gamingTournament.fragment.PubgFragment;
import com.gamingTournament.gamingTournament.fragment.RefundFragment;
import com.gamingTournament.gamingTournament.fragment.WalletFragment;
import com.gamingTournament.gamingTournament.fragment.tncFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends FragmentActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, MainAdapter.OnItemClickListener {
        private String salt = "GT397PB";
        private ImageView image;
        private int n=0;
        private DrawerLayout drawer;
        private ActionBarDrawerToggle mToggle;
        private Paytm paytm;
        private Retrofit retrofit;
        private PaytmApi apiService;
        private Users user;
        private TextView balanceBox;
        RecyclerView recyclerView;
        MainAdapter adapter;
        LinearLayoutManager manager;
        ApiInterface apiInterface;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        user = getUserBalance();

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setActionBar(toolbar);

        View child = toolbar.getChildAt(1);

        balanceBox = findViewById(child.getId());

        drawer = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        assert getActionBar()!=null;
        getActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.main_recycler_view);
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();
        recyclerView.setItemViewCacheSize(20);
        adapter=new MainAdapter(this);
        recyclerView.setAdapter(adapter);

        NavigationView navigationView = findViewById(R.id.side_nav_bar);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        TextView headerName =(TextView)headerView.findViewById(R.id.realNameHeader);


        headerName.setText(user.getReal_name());

        balanceBox.setText("₹ "+user.getBalance());

            balanceBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.changeDrawerFragment(MainActivity.this, new WalletFragment());
                }
            });

       }

    public Users getUserBalance() {

        user = SharedPrefManager.getInstance(MainActivity.this).getUser();

        Call<ResponseBody> call = apiInterface.getBalance(salt,user.getUname());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    if(!s.equals("error"))
                    {
                        SharedPrefManager.getInstance(MainActivity.this).updateBalance(s);
                        setUserBalance(s);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

        return SharedPrefManager.getInstance(MainActivity.this).getUser();
    }

    public void setUserBalance(String balance)
    {
        balanceBox.setText("₹ "+balance);
    }


    public void clicklistener(Class<? extends Activity> c)
       {
           Intent intent =new Intent(MainActivity.this,c);
           startActivity(intent);
       }


    @Override
    protected void onStart() {
        super.onStart();

        Call<List<update_details>> call = apiInterface.updateDetails(salt);
        call.enqueue(new Callback<List<update_details>>() {
            @Override
            public void onResponse(Call<List<update_details>> call, Response<List<update_details>> response) {
                final update_details item = response.body().get(0);
                String currVersion = BuildConfig.VERSION_NAME;
                String newVersion = item.getVersion();

                if (!currVersion.equals(newVersion)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_update, null);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    TextView btnLater, btnUpdate, updateText;

                    btnLater = dialogView.findViewById(R.id.btn_later);
                    btnUpdate = dialogView.findViewById(R.id.btn_update);
                    updateText = dialogView.findViewById(R.id.update_dialog_textView);

                    dialog.show();
                    dialog.setCancelable(false);

                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(item.getUpdate_url());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });

                    if(item.getCritical_update().equals("true"))
                    {
                        updateText.setText(R.string.critical_update_text);

                        btnLater.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }
                    else
                    {
                        dialog.setCancelable(true);

                        updateText.setText(R.string.normal_update_text);

                        btnLater.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                }

                else if(!SharedPrefManager.getInstance(MainActivity.this).isLoggedIn()){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<update_details>> call, Throwable t) {

            }
        });

        user=getUserBalance();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {

            n = v.getId();
        switch (n)
        {

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

                case R.id.events:  Util.changeDrawerFragment(MainActivity.this, new EventsFragment());
                    drawer.closeDrawer(Gravity.LEFT);
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

                case R.id.profile: Util.changeDrawerFragment(MainActivity.this, new ProfileFragment());
                    drawer.closeDrawer(Gravity.LEFT);
                    break;

                case R.id.wallet: Util.changeDrawerFragment(MainActivity.this, new WalletFragment());
                    drawer.closeDrawer(Gravity.LEFT);
                    break;

                case R.id.top_players:Util.changeDrawerFragment(MainActivity.this, new TopPlayersFragment());
                                       drawer.closeDrawer(Gravity.LEFT);
                                        break;

                case R.id.shareApp: Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Do you know you can win Free Paytm Cash by Playing PUBG , LUDO , FREE FIRE and other mobile games?\n" +
                            "\n" +
                            "Yes, now get paid for playing games and every Kill. Not only this, if you get Chicken Dinner, you really win Big Prizes.\n" +
                            "\n" +
                            "Just Download & Try Gaming Tournament App: http://gamingtournament.in/download/gamingtournament.apk");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent,"Share via"));
                                    break;
                case R.id.logout: logout();
                                    break;
            }
            return true;
    }

    private void logout() {
        SharedPrefManager.getInstance(MainActivity.this).clear();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onCardClick(int position) {
        switch (position)
        {
            case 0: Util.changeGameFragment(MainActivity.this, new PubgFragment());
                break;

            case 1: Util.changeGameFragment(MainActivity.this, new FreefireFragment());
                break;

            case 2: Util.changeGameFragment(MainActivity.this, new MinimFragment());
                break;

            case 3: Util.changeGameFragment(MainActivity.this, new LudoFragment());
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack("MainActivity",-1);
    }
}

