package com.gamingTournament.gamingTournament.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LudoPlayAdapter extends RecyclerView.Adapter<LudoPlayAdapter.MyViewHolder> {
    private OnItemClickListener listener;
    private List<list_play> matchDetails;

    public LudoPlayAdapter(OnItemClickListener listener, List<list_play> matchDetails) {
        this.listener = listener;
        this.matchDetails = matchDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_ludo_play, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        list_play item = matchDetails.get(position);

        if(Integer.parseInt(item.getEntryStatus())==1) {
            int maxPlayers=Integer.parseInt(item.getMaxPlayers());
            int playersJoined=Integer.parseInt(item.getPlayerJoined());
            String dateTime = item.getDateTime();
            DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH for hour of the day (0 - 23)
            Date d = null;
            try {
                d = f1.parse(dateTime);
                DateFormat f2 = new SimpleDateFormat("yyyy-MM-dd  hh:mma");
                dateTime = f2.format(d).toLowerCase(); // "12:18am"
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.matchID.setText(item.getMatchID());
            holder.matchTitle.setText(item.getMatchTitle());
            holder.dateTime.setText(dateTime);
            holder.winPrize.setText("₹" + item.getWinPrize());
            holder.entryFee.setText("₹ " + item.getEntryFee());
            holder.maxPlayers.setText(item.getMaxPlayers());
            holder.playersJoined.setText(item.getPlayerJoined() + "/"+item.getMaxPlayers());
            holder.imageView.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(Integer.parseInt(item.getPlayerJoined()));
            holder.progressBar.setMax(Integer.parseInt(item.getMaxPlayers()));
            holder.spotsLeft.setText(String.valueOf(maxPlayers-playersJoined)+" Spots Left!");

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

        TextView matchID,dateTime,winPrize,entryFee,playersJoined,maxPlayers,matchTitle,spotsLeft;
        ImageView imageView;
        ProgressBar progressBar;
        Button join;

        MyViewHolder(View view){
            super(view);
            spotsLeft =view.findViewById(R.id.textView171);
            imageView = view.findViewById(R.id.image2);
            matchTitle=view.findViewById(R.id.textView111);
            winPrize = view.findViewById(R.id.textView611);
            entryFee = view.findViewById(R.id.textView711);
            maxPlayers =view.findViewById(R.id.textView811);
            matchID = view.findViewById(R.id.textView181);
            dateTime = view.findViewById(R.id.textView191);
            progressBar = view.findViewById(R.id.progressBar2);
            playersJoined = view.findViewById(R.id.textView161);
            join = view.findViewById(R.id.textView151);
        }
    }

    public interface OnItemClickListener{
        public void onAdapterClick(int position);
        public void onJoinClick(int position);


    }
}
