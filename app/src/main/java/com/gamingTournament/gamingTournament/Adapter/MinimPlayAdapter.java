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
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MinimPlayAdapter extends RecyclerView.Adapter<MinimPlayAdapter.MyViewHolder> {
    private OnItemClickListener listener;
    private List<list_play> matchDetails;
    String URL ="http://gamingtournament.in/appimg/";

    public MinimPlayAdapter(OnItemClickListener listener, List<list_play> matchDetails) {
        this.listener = listener;
        this.matchDetails = matchDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_minim_play, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        list_play item = matchDetails.get(position);

        if(Integer.parseInt(item.getEntryStatus())==1) {
            int maxPlayers=Integer.parseInt(item.getMaxPlayers());
            int playersJoined=Integer.parseInt(item.getPlayerJoined());
            Picasso.get()
                    .load(URL+"minim.jpg")
                    .fit()
                    .into(holder.imageView);
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
            holder.map.setText(item.getMap());
            holder.matchTitle.setText(item.getMatchTitle()+" -Match#"+item.getMatchID());
            holder.dateTime.setText(dateTime);
            holder.winPrize.setText("₹" + item.getWinPrize());
            holder.entryFee.setText("₹ " + item.getEntryFee());
            holder.playersJoined.setText(item.getPlayerJoined() + "/"+item.getMaxPlayers());
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

        TextView dateTime,winPrize,entryFee,map,playersJoined,matchTitle,spotsLeft;
        ImageView imageView;
        ProgressBar progressBar;
        Button join;

        MyViewHolder(View view){
            super(view);
            map=view.findViewById(R.id.textView722);
            spotsLeft =view.findViewById(R.id.textView172);
            imageView = view.findViewById(R.id.image3);
            matchTitle=view.findViewById(R.id.textView222);
            winPrize = view.findViewById(R.id.textView622);
            entryFee = view.findViewById(R.id.textView822);
            dateTime = view.findViewById(R.id.textView192);
            progressBar = view.findViewById(R.id.progressBar3);
            playersJoined = view.findViewById(R.id.textView162);
            join = view.findViewById(R.id.textView152);
        }
    }

    public interface OnItemClickListener{
        public void onAdapterClick(int position);
        public void onJoinClick(int position);
    }
}
