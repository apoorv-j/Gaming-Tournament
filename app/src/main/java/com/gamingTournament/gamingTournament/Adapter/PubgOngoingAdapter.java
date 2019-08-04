package com.gamingTournament.gamingTournament.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PubgOngoingAdapter extends RecyclerView.Adapter<PubgOngoingAdapter.MyViewHolder> {
    private List<list_play> matchDetails;
    private OnItemClickListener listener;

    public PubgOngoingAdapter(OnItemClickListener listener, List<list_play> list_plays){
        this.listener = listener;
        this.matchDetails = list_plays;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pubg_ongoing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        list_play item = matchDetails.get(position);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currDate = mdformat.format(calendar.getTime());
        String dateTime = item.getDateTime();

        try {
            Date cDate= mdformat.parse(currDate);
            Date mDate= mdformat.parse(dateTime);
            if(cDate.after(mDate) && Integer.parseInt(item.getEntryStatus())==1)
            {
                DateFormat f2 = new SimpleDateFormat("dd-MM-yyyy   hh:mm a");
                dateTime = f2.format(mDate).toLowerCase(); // "12:18am"

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

        } catch (ParseException e) {
            e.printStackTrace();
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
        MyViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.po_cardView);
            imageView = view.findViewById(R.id.po_image);
            killPrize = view.findViewById(R.id.po_textView7);
            winPrize = view.findViewById(R.id.po_textView6);
            entryFee = view.findViewById(R.id.po_textView8);
            type = view.findViewById(R.id.po_textView12);
            mode = view.findViewById(R.id.po_textView13);
            map = view.findViewById(R.id.po_textView14);
            matchID = view.findViewById(R.id.po_textView);
            dateTime = view.findViewById(R.id.po_textView2);
            spectate = view.findViewById(R.id.po_textView15);
        }
    }

    public interface OnItemClickListener{
        public void onClickListener(int position);
        public void spectateListener(int position, String youtubeID);
    }
}
