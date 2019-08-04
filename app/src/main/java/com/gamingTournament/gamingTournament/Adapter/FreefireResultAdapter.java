package com.gamingTournament.gamingTournament.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FreefireResultAdapter extends RecyclerView.Adapter<FreefireResultAdapter.MyViewHolder> {

    private List<list_play> matchDetails;
    private OnItemClickListener listener;
    String URL ="http://gamingtournament.in/appimg/";

    public FreefireResultAdapter(OnItemClickListener listener,List<list_play> matchDetails) {
        this.matchDetails = matchDetails;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_freefire_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        list_play item = matchDetails.get(position);

        if(Integer.parseInt(item.getEntryStatus())!=1)
        {
            Picasso.get()
                    .load(URL+"freefire.jpg")
                    .fit()
                    .into(holder.imageView);
            String dateTime = item.getDateTime();
            DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH for hour of the day (0 - 23)
            Date d = null;
            try {
                d = f1.parse(dateTime);
                DateFormat f2 = new SimpleDateFormat("dd-MM-yyyy   hh:mm a");
                dateTime = f2.format(d).toLowerCase(); // "12:18am"
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.matchID.setText(item.getMatchTitle()+" - Match#"+item.getMatchID());
            holder.dateTime.setText(dateTime);
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
        return matchDetails.size();    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView matchID,dateTime,killPrize,winPrize,entryFee,mode,type,map,spectate;
        ImageView imageView;
        CardView cardView;
        MyViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.fr_cardView);
            imageView = view.findViewById(R.id.fr_image);
            killPrize = view.findViewById(R.id.fr_textView7);
            winPrize = view.findViewById(R.id.fr_textView6);
            entryFee = view.findViewById(R.id.fr_textView8);
            type = view.findViewById(R.id.fr_textView12);
            mode = view.findViewById(R.id.fr_textView13);
            map = view.findViewById(R.id.fr_textView14);
            matchID = view.findViewById(R.id.fr_textView);
            dateTime = view.findViewById(R.id.fr_textView2);
            spectate = view.findViewById(R.id.fr_textView16);
        }
    }

    public interface OnItemClickListener{
        public void onClickListener(int position);
        public void spectateListener(int position, String youtubeID);
    }
}
