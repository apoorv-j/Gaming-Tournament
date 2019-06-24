package com.gamingTournament.gamingTournament.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamingTournament.gamingTournament.Adapter.PubgOngoingAdapter;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.PlayViewModel;
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
public class OngoingFragment extends Fragment implements PubgOngoingAdapter.OnItemClickListener {


    public OngoingFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    PubgOngoingAdapter adapter;
    List<list_play> matchDetails;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);


        recyclerView = view.findViewById(R.id.po_recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(manager);

        PlayViewModel model = ViewModelProviders.of(OngoingFragment.this).get(PlayViewModel.class);

        model.matchDetails(getContext()).observe(OngoingFragment.this, new Observer<List<list_play>>() {
            @Override
            public void onChanged(@NonNull List<list_play> list_plays) {
                String status = list_plays.get(0).getStatus();
                if(status == null) {
                    matchDetails = list_plays;
                    adapter = new PubgOngoingAdapter(OngoingFragment.this, list_plays);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        return view;
    }

    @Override
    public void onClickListener(int position) {
        Intent intent = new Intent(getActivity(), PubgMatchDetailActivity.class);
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
