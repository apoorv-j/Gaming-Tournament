package com.gamingTournament.gamingTournament.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamingTournament.gamingTournament.PubgPlayAdapter;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.activity.MatchDetailActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment implements PubgPlayAdapter.OnItemClickListener {

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

       
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        PubgPlayAdapter adapter = new PubgPlayAdapter(this);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClickListener(int position) {
        startActivity(new Intent(getActivity(), MatchDetailActivity.class));
    }
}
