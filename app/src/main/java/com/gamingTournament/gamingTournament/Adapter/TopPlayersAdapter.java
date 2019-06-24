package com.gamingTournament.gamingTournament.Adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.Lists.list_top_players;
import com.gamingTournament.gamingTournament.R;

import java.util.List;

public class TopPlayersAdapter extends RecyclerView.Adapter<TopPlayersAdapter.MyViewHolder>{

    List<list_top_players> top_players;

    public TopPlayersAdapter(List<list_top_players> top_players) {
        this.top_players = top_players;
    }

    @NonNull
    @Override
    public TopPlayersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_top_players, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlayersAdapter.MyViewHolder holder, int position) {
            list_top_players item = top_players.get(position);
            Integer pos = Integer.parseInt(item.getPosition());
            if(pos==1||pos==2||pos==3) {
                holder.winnings.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            holder.position.setText(item.getPosition());
            holder.playerName.setText(item.getUser_names());
            holder.winnings.setText("₹ "+item.getMoney_earned());
    }

    @Override
    public int getItemCount() {
        return top_players.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView position,playerName,winnings;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            position=itemView.findViewById(R.id.tp_pos);
            playerName=itemView.findViewById(R.id.tp_name);
            winnings=itemView.findViewById(R.id.tp_winnings);

        }
    }
}
