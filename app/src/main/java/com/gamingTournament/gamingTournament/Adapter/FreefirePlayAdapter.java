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
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FreefirePlayAdapter extends RecyclerView.Adapter<FreefirePlayAdapter.MyViewHolder> {

    private List<list_play> matchDetails;
    private Context context;
    private OnItemClickListener listener;
    private String URL ="http://gamingtournament.in/appimg/";

    public FreefirePlayAdapter(OnItemClickListener listener, List<list_play> list_plays){
        this.listener = listener;
        this.matchDetails = list_plays;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_freefire_play, parent, false));
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



            Picasso.get()
                    .load(URL+"freefire.jpg")
                    .fit()
                    .into(holder.imageView);
            holder.matchTitle.setText(item.getMatchTitle()+" -Match#"+item.getMatchID());
            holder.dateTime.setText(dateTime);
            holder.winPrize.setText("₹" + item.getWinPrize());
            holder.killPrize.setText("₹ " + item.getKillPrize());
            holder.entryFee.setText("₹ " + item.getEntryFee());
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

            holder.progressBar.setProgress(Integer.parseInt(item.getPlayerJoined()));
            holder.progressBar.setMax(Integer.parseInt(item.getMaxPlayers()));
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

        TextView dateTime,killPrize,winPrize,entryFee,type,map,playersJoined,matchTitle,posLeft;
        ImageView imageView;
        ProgressBar progressBar;
        Button join;

        MyViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.fp_image1);
            matchTitle=view.findViewById(R.id.fp_textView000);
            killPrize = view.findViewById(R.id.fp_textView700);
            winPrize = view.findViewById(R.id.fp_textView600);
            entryFee = view.findViewById(R.id.fp_textView800);
            type = view.findViewById(R.id.fp_textView120);
            map = view.findViewById(R.id.fp_textView130);
            dateTime = view.findViewById(R.id.fp_textView190);
            progressBar = view.findViewById(R.id.progressBar1);
            playersJoined = view.findViewById(R.id.fp_textView160);
            join = view.findViewById(R.id.fp_textView150);
            posLeft=view.findViewById(R.id.fp_pos_left);
        }
    }

    public interface OnItemClickListener{
        public void onAdapterClick(int position);
        public void onJoinClick(int position);

    }
}
