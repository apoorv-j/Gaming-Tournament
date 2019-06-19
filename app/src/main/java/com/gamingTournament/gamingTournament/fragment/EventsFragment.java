package com.gamingTournament.gamingTournament.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.Adapter.EventAdapter;
import com.gamingTournament.gamingTournament.Lists.list_events;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.EventsViewModel;

import java.util.List;

public class EventsFragment extends Fragment implements EventAdapter.OnItemClickListener{

    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    List<list_events> eventsList;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.events_fragment, container, false);

        recyclerView = view.findViewById(R.id.event_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();

        EventsViewModel model = ViewModelProviders.of(EventsFragment.this).get(EventsViewModel.class);
        model.getEvents(getContext()).observe(EventsFragment.this, new Observer<List<list_events>>() {
            @Override
            public void onChanged(List<list_events> list_events) {
                eventsList=list_events;
                String status=eventsList.get(0).getStatus();
                if(!status.equals("no_events_found")) {
                    eventAdapter=new EventAdapter(eventsList,EventsFragment.this);
                    recyclerView.setAdapter(eventAdapter);
                }
                else
                {
                    Toast.makeText(getContext(), "No Events!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void LinkClick(int position) {

    }
}
