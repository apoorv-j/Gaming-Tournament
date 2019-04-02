package com.gamingTournament.gamingTournament.fragment;


import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.activity.MainActivity;
import com.gamingTournament.gamingTournament.activity.PubgActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PubgFragment extends Fragment {


    public PubgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pubg, container, false);

        BottomNavigationView navigation = view.findViewById(R.id.bottom_nav_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ongoing:
                    Util.changeFragment(Objects.requireNonNull(getActivity()), new OngoingFragment());
                    return true;
                case R.id.navigation_play:
                    Util.changeFragment(Objects.requireNonNull(getActivity()), new PlayFragment());
                    return true;
                case R.id.navigation_me:
                    Util.changeFragment(Objects.requireNonNull(getActivity()), new MeFragment());
                    return true;
                case R.id.navigation_result:
                    Util.changeFragment(Objects.requireNonNull(getActivity()), new ResultFragment());
                    return true;

            }
            return true;
        }
    };

}
