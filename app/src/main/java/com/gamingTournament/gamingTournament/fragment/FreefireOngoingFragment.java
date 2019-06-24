package com.gamingTournament.gamingTournament.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamingTournament.gamingTournament.Adapter.FreefireOngoingAdapter;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.FreefirePlayViewModel;
import com.gamingTournament.gamingTournament.activity.FreefireMatchDetailActivity;
import com.gamingTournament.gamingTournament.activity.PubgMatchDetailActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreefireOngoingFragment extends Fragment implements FreefireOngoingAdapter.OnItemClickListener {


    public FreefireOngoingFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    FreefireOngoingAdapter adapter;
    List<list_play> freefireMatchDetails;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);


        recyclerView = view.findViewById(R.id.po_recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(manager);

        FreefirePlayViewModel model = ViewModelProviders.of(FreefireOngoingFragment.this).get(FreefirePlayViewModel.class);

        model.freefireMatchDetails(getContext()).observe(FreefireOngoingFragment.this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(@NonNull List<list_play> list_plays) {
                freefireMatchDetails = list_plays;
                adapter = new FreefireOngoingAdapter(FreefireOngoingFragment.this, list_plays);
                recyclerView.setAdapter(adapter);

            }
        });
        return view;
    }

    @Override
    public void onClickListener(int position) {
        Intent intent = new Intent(getActivity(), FreefireMatchDetailActivity.class);
        intent.putExtra("position",String.valueOf(position));
        startActivity(intent);
    }

    @Override
    public void spectateListener(int position, String youtubeID) {
        String ID = youtubeID.substring(youtubeID.indexOf("=")+1);
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + ID));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + ID));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
