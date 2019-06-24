package com.gamingTournament.gamingTournament.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Adapter.TopPlayersAdapter;
import com.gamingTournament.gamingTournament.Lists.list_top_players;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.TopPlayerViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopPlayersFragment extends Fragment {


    public TopPlayersFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    TopPlayersAdapter adapter;
    List<list_top_players> topPlayers;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_players, container, false);

        recyclerView = view.findViewById(R.id.tp_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        TopPlayerViewModel model = ViewModelProviders.of(TopPlayersFragment.this).get(TopPlayerViewModel.class);

        model.getDetails(getContext()).observe(TopPlayersFragment.this, new Observer<List<list_top_players>>() {
            @Override
            public void onChanged(List<list_top_players> list_top_players) {
                String status = list_top_players.get(0).getStatus();
                if(status==null){
                    adapter= new TopPlayersAdapter(list_top_players);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        return view;
    }

}
