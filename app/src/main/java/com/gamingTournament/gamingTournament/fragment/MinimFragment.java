package com.gamingTournament.gamingTournament.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MinimFragment extends Fragment {


    public MinimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pubg, container, false);

        BottomNavigationView navigation = view.findViewById(R.id.bottom_nav_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Initially play fragment will start
        navigation.setSelectedItemId(R.id.navigation_play);

        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ongoing:

                case R.id.navigation_play:
                    Util.changeFragment(Objects.requireNonNull(getActivity()), new MinimPlayFragment());
                    break;
                case R.id.navigation_result:
                    Util.changeFragment(Objects.requireNonNull(getActivity()), new MinimResultFragment());
                    break;

            }
            return true;
        }
    };

}
