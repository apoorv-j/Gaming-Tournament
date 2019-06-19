package com.gamingTournament.gamingTournament.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.Lists.list_winner;
import com.gamingTournament.gamingTournament.R;

import java.util.List;

public class WinnerAdapter extends RecyclerView.Adapter<WinnerAdapter.MyViewHolder> {

    private List<list_winner> matchWinner;


    public WinnerAdapter(List<list_winner> matchWinner) {
        this.matchWinner = matchWinner;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_winner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        list_winner item = matchWinner.get(position);


            holder.wNo.setText(String.valueOf("●"));
            holder.wName.setText(item.getWinnerName());

    }

    @Override
    public int getItemCount() {
        return matchWinner.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cardView;
        TextView wName,wNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView =itemView.findViewById(R.id.participants_card);
            wName = itemView.findViewById(R.id.participants_name);
            wNo = itemView.findViewById(R.id.participants_no);
        }
    }
}
