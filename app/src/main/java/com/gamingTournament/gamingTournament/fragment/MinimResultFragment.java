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

import com.gamingTournament.gamingTournament.Adapter.MinimResultAdapter;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.MinimPlayViewModel;

import java.util.List;


public class MinimResultFragment extends Fragment {


    public MinimResultFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    MinimResultAdapter adapter;
    List<list_play> matchDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());


        recyclerView.setLayoutManager(manager);
        MinimPlayViewModel model = ViewModelProviders.of(MinimResultFragment.this).get(MinimPlayViewModel.class);

        model.minimMatchDetails(getContext()).observe(MinimResultFragment.this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(@NonNull List<list_play> list_plays) {
                String status = list_plays.get(0).getStatus();
                if(status == null) {
                    matchDetails = list_plays;
                    adapter = new MinimResultAdapter(list_plays,MinimResultFragment.this);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        return view;
    }

}
