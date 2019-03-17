package com.gamingTournament.gamingTournament.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamingTournament.gamingTournament.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        if (getActivity() != null && getActivity().getActionBar() != null){
            getActivity().getActionBar().hide();
        }

        return view;
    }

}
