package com.gamingTournament.gamingTournament.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.Lists.list_play;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PubgPlayAdapter extends RecyclerView.Adapter<PubgPlayAdapter.MyViewHolder> {

    private List<list_play> matchDetails;
    private Context context;
    private OnItemClickListener listener;

    public PubgPlayAdapter(OnItemClickListener listener, List<list_play> list_plays){
        this.listener = listener;
        this.matchDetails = list_plays;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_pubg_play, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        list_play item = matchDetails.get(position);

        if(Integer.parseInt(item.getEntryStatus())==1) {
            int maxPlayers=Integer.parseInt(item.getMaxPlayers());
            int playersJoined=Integer.parseInt(item.getPlayerJoined());

            holder.matchID.setText(item.getMatchID());
            holder.matchTitle.setText(item.getMatchTitle()+" - ");
            holder.dateTime.setText(item.getDateTime());
            holder.winPrize.setText("₹" + item.getWinPrize());
            holder.killPrize.setText("₹ " + item.getKillPrize());
            holder.entryFee.setText("₹ " + item.getEntryFee());
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
            holder.playersJoined.setText(item.getPlayerJoined() + "/"+item.getMaxPlayers());
            holder.imageView.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(Integer.parseInt(item.getPlayerJoined()));
            holder.posLeft.setText(String.valueOf(maxPlayers-playersJoined)+" Spots Left!");

            if (maxPlayers<=playersJoined) {
                holder.join.setText("Match full");
                holder.join.setBackgroundColor(Color.parseColor("#616161"));
                holder.join.setClickable(false);
            }
            else {
                holder.join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onJoinClick(position);
                    }
                });
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(position);
                }
            });
        }

        else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

        }


    }

    @Override
    public int getItemCount() {
        return matchDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView matchID,matchTitle,dateTime,killPrize,winPrize,entryFee,mode,type,map,playersJoined,posLeft;
        ImageView imageView;
        ProgressBar progressBar;
        Button join;
        CardView cardView;

        MyViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.pp_cardView);
            imageView = view.findViewById(R.id.pp_image);
            killPrize = view.findViewById(R.id.pp_textView7);
            winPrize = view.findViewById(R.id.pp_textView6);
            entryFee = view.findViewById(R.id.pp_textView8);
            type = view.findViewById(R.id.pp_textView12);
            mode = view.findViewById(R.id.pp_textView13);
            map = view.findViewById(R.id.pp_textView14);
            matchID = view.findViewById(R.id.pp_textView18);
            matchTitle = view.findViewById(R.id.pp_textView);
            dateTime = view.findViewById(R.id.pp_textView19);
            progressBar = view.findViewById(R.id.progressBar);
            playersJoined = view.findViewById(R.id.pp_textView16);
            join = view.findViewById(R.id.pp_join);
            posLeft=view.findViewById(R.id.pp_pos_left);

        }
    }

    public interface OnItemClickListener{
        public void onAdapterClick(int position);
        public void onJoinClick(int position);

    }
}
