package com.gamingTournament.gamingTournament.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.Lists.list_match_results;
import com.gamingTournament.gamingTournament.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FullResultAdapter extends RecyclerView.Adapter<FullResultAdapter.MyViewHolder> {

    private List<list_match_results> matchResults;
    private String matchID;
    private String per_kill;
    private int sNo=0;
    public FullResultAdapter(List<list_match_results> matchResults, String matchID, String per_kill) {
        this.matchResults = matchResults;
        this.matchID = matchID;
        this.per_kill = per_kill;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pubg_full_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        list_match_results item = matchResults.get(position);
        if(matchID.equals(item.getMatchID()))
        {
            holder.pID.setText("●");
            holder.pName.setText(item.getPlayerID());
            holder.pKills.setText(item.getKills());
            int winnings = Integer.parseInt(item.getKills())*Integer.parseInt(per_kill);
            holder.pWinnings.setText(String.valueOf(winnings));
        }
        else
        {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    @Override
    public int getItemCount() {
        return matchResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pID, pName, pKills, pWinnings;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            pID = itemView.findViewById(R.id.fr_textView32);
            pName = itemView.findViewById(R.id.fr_textView31);
            pKills = itemView.findViewById(R.id.fr_textView34);
            pWinnings = itemView.findViewById(R.id.fr_textView33);

        }
    }
}
