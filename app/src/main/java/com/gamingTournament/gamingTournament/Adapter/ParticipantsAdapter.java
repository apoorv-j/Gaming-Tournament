package com.gamingTournament.gamingTournament.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.Lists.list_match_results;
import com.gamingTournament.gamingTournament.Lists.list_participants;
import com.gamingTournament.gamingTournament.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.MyViewHolder> {

    private List<list_participants> participantsList;



    public ParticipantsAdapter(List<list_participants> participantsList) {
        this.participantsList = participantsList;
    }

    @NonNull
    @Override
    public ParticipantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_participants, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsAdapter.MyViewHolder holder, int position) {
        list_participants item = participantsList.get(position);

            String playerString = item.getIn_game_names();
            String[] playerNames= playerString.split("/");
            int no_of_players = playerNames.length;
            holder.player_no.setText("●");
            switch (no_of_players)
            {
                case 1: holder.player_name.setText(playerNames[0]);
                        break;
                case 2:  holder.player_name.setText(playerNames[0]+"\n"+playerNames[1]);
                    break;
                case 3:  holder.player_name.setText(playerNames[0]+"\n"+playerNames[1]+"\n"+playerNames[2]);
                    break;
                case 4:  holder.player_name.setText(playerNames[0]+"\n"+playerNames[1]+"\n"+playerNames[2]+"\n"+playerNames[3]);
                    break;
            }


    }

    @Override
    public int getItemCount() {
        return participantsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView player_no,player_name;
        ConstraintLayout cardView;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            cardView =itemView.findViewById(R.id.participants_card);
            player_no = itemView.findViewById(R.id.participants_no);
            player_name= itemView.findViewById(R.id.participants_name);

        }
    }
}
