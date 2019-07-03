package com.gamingTournament.gamingTournament.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FreefireOngoingAdapter extends RecyclerView.Adapter<FreefireOngoingAdapter.MyViewHolder> {
    private List<list_play> freefireMatchDetails;
    private OnItemClickListener listener;
    String URL ="http://gamingtournament.in/appimg/";

    public FreefireOngoingAdapter(OnItemClickListener listener, List<list_play> list_plays){
        this.listener = listener;
        this.freefireMatchDetails = list_plays;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_freefire_ongoing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        list_play item = freefireMatchDetails.get(position);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currDate = mdformat.format(calendar.getTime());
        String dateTime = item.getDateTime();

        if(dateTime!=null) {
            try {
                Date cDate = mdformat.parse(currDate);
                Date mDate = mdformat.parse(dateTime);

                if (cDate.after(mDate) && Integer.parseInt(item.getEntryStatus()) == 1) {
                    Log.e("onBindViewHolder: ", "in");
                    Picasso.get()
                            .load(URL + "freefire.jpg")
                            .fit()
                            .into(holder.imageView);

                    DateFormat f2 = new SimpleDateFormat("yyyy-MM-dd  hh:mma");
                    dateTime = f2.format(mDate).toLowerCase(); // "12:18am"

                    holder.matchID.setText(item.getMatchTitle()+" - "+item.getMatchID());
                    holder.dateTime.setText(dateTime);
                    holder.winPrize.setText("₹" + item.getWinPrize());
                    holder.killPrize.setText("₹ " + item.getKillPrize());
                    holder.entryFee.setText("₹ " + item.getEntryFee());

                    holder.map.setText(item.getMap());
                    switch (item.getTeamSize()) {
                        case "1":
                            holder.type.setText("solo");
                            break;
                        case "2":
                            holder.type.setText("duo");
                            break;
                        case "4":
                            holder.type.setText("squad");
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
                            listener.spectateListener(position, youtubeID);
                        }
                    });
                } else {
                    Log.e("onBindViewHolder: ", "out");
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return freefireMatchDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView matchID,dateTime,killPrize,winPrize,entryFee,type,map,spectate;
        ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.fo_image1);
            killPrize = view.findViewById(R.id.fo_textView700);
            winPrize = view.findViewById(R.id.fo_textView600);
            entryFee = view.findViewById(R.id.fo_textView800);
            type = view.findViewById(R.id.fo_textView120);
            map = view.findViewById(R.id.fo_textView130);
            matchID = view.findViewById(R.id.fo_textView180);
            dateTime = view.findViewById(R.id.fo_textView190);
            spectate = view.findViewById(R.id.fo_textView15);
        }
    }

    public interface OnItemClickListener{
        public void onClickListener(int position);
        public void spectateListener(int position, String youtubeID);
    }
}
