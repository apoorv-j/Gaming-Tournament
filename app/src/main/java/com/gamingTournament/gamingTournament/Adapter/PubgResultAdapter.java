package com.gamingTournament.gamingTournament.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PubgResultAdapter extends RecyclerView.Adapter<PubgResultAdapter.MyViewHolder> {

    private List<list_play> matchDetails;
    private OnItemClickListener listener;

    public PubgResultAdapter(OnItemClickListener listener, List<list_play> list_plays){
        this.listener = listener;
        this.matchDetails = list_plays;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pubg_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        list_play item = matchDetails.get(position);

        if(Integer.parseInt(item.getEntryStatus())!=1)
        {
            holder.matchID.setText(item.getMatchTitle());
            holder.dateTime.setText(item.getDateTime());
            holder.winPrize.setText("₹"+item.getWinPrize());
            holder.killPrize.setText("₹ "+item.getKillPrize());
            holder.entryFee.setText("₹ "+item.getEntryFee());
            holder.mode.setText(item.getMode());
            holder.map.setText(item.getMap());
            switch (item.getTeamSize()) {
                case "1": holder.type.setText("solo");
                    break;
                case "2": holder.type.setText("duo");
                    break;
                case "4": holder.type.setText("squad");
                    break;
            }
            holder.imageView.setVisibility(View.VISIBLE);

            final String youtubeID = item.getYoutube();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(position);
                }
            });

            holder.spectate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.spectateListener(position,youtubeID);
                }
            });
        }
        else
        {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }


    }

    @Override
    public int getItemCount() {
        return matchDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView matchID,dateTime,killPrize,winPrize,entryFee,mode,type,map,spectate;
        ImageView imageView;
        CardView cardView;
        MyViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.pr_cardView);
            imageView = view.findViewById(R.id.pr_image);
            killPrize = view.findViewById(R.id.pr_textView7);
            winPrize = view.findViewById(R.id.pr_textView6);
            entryFee = view.findViewById(R.id.pr_textView8);
            type = view.findViewById(R.id.pr_textView12);
            mode = view.findViewById(R.id.pr_textView13);
            map = view.findViewById(R.id.pr_textView14);
            matchID = view.findViewById(R.id.pr_textView);
            dateTime = view.findViewById(R.id.pr_textView2);
            spectate = view.findViewById(R.id.pr_textView16);
        }
    }

    public interface OnItemClickListener{
        public void onClickListener(int position);
        public void spectateListener(int position, String youtubeID);
    }
}
