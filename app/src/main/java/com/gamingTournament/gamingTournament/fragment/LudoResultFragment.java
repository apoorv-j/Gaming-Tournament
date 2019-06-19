package com.gamingTournament.gamingTournament.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.Adapter.LudoResultAdapter;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.Lists.list_winner;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.LudoPlayViewModel;
import com.gamingTournament.gamingTournament.ViewModels.PlayViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LudoResultFragment extends Fragment {


    public LudoResultFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    LudoResultAdapter adapter;
    List<list_play> matchDetails;
    List<list_winner> winnerList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());


        recyclerView.setLayoutManager(manager);
        LudoPlayViewModel model = ViewModelProviders.of(LudoResultFragment.this).get(LudoPlayViewModel.class);
        model.ludoMatchDetails(getContext()).observe(LudoResultFragment.this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(@NonNull List<list_play> list_plays) {
                String status = list_plays.get(0).getStatus();
                if(status == null) {
                matchDetails = list_plays;
                adapter = new LudoResultAdapter(list_plays,LudoResultFragment.this);
                recyclerView.setAdapter(adapter);
            }
            }
        });
        return view;
    }

}
