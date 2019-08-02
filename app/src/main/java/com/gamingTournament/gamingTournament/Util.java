package com.gamingTournament.gamingTournament;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class Util {

    public static void changeFragment(FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }
    public static void changeDrawerFragment(FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_constraint, fragment)
                .addToBackStack("MainActivity")
                .commit();
    }

    public static void changeGameFragment(FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_constraint, fragment)
                .addToBackStack(null)
                .commit();
    }
}
