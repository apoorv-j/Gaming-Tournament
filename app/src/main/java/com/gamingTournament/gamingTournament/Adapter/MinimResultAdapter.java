package com.gamingTournament.gamingTournament.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.Lists.list_winner;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.WinnerViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MinimResultAdapter extends RecyclerView.Adapter<MinimResultAdapter.MyViewHolder>{
    private List<list_play> matchDetails;
    private Fragment context;
    WinnerAdapter winnerAdapter;
    String URL ="http://gamingtournament.in/appimg/";


    public MinimResultAdapter(List<list_play> matchDetails, Fragment context) {
        this.matchDetails = matchDetails;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_minim_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        list_play item = matchDetails.get(position);

        if(Integer.parseInt(item.getEntryStatus())!=1)
        {
            Picasso.get()
                    .load(URL+"minim.jpg")
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
            holder.matchTitle.setText(item.getMatchTitle()+"-Match#"+item.getMatchID());
            holder.dateTime.setText(dateTime);
            holder.winPrize.setText("₹"+item.getWinPrize());
            holder.entryFee.setText("₹ "+item.getEntryFee());
            holder.playerJoned.setText(item.getPlayerJoined());
            ViewModel winnermodel = ViewModelProviders.of(context).get(WinnerViewModel.class);
            ((WinnerViewModel) winnermodel).matchWinner(item.getMatchID(),"minimilitia").observe(context, new Observer<List<list_winner>>() {
                @Override
                public void onChanged(List<list_winner> list_winners) {
                    String status=list_winners.get(0).getStatus();
                    if(status==null)
                    {
                        winnerAdapter= new WinnerAdapter(list_winners);
                        holder.recyclerView.setAdapter(winnerAdapter);
                    }
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView matchTitle,dateTime,winPrize,entryFee,playerJoned;
        ImageView imageView;
        RecyclerView recyclerView;
        LinearLayoutManager manager;

        public MyViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.mr_image);
            winPrize = view.findViewById(R.id.mr_textView6);
            entryFee = view.findViewById(R.id.mr_textView8);
            playerJoned = view.findViewById(R.id.mr_textView14);
            matchTitle = view.findViewById(R.id.mr_textView);
            dateTime = view.findViewById(R.id.mr_textView2);
            recyclerView = view.findViewById(R.id.minim_recycler_view);
            manager=new LinearLayoutManager(context.getContext());
            recyclerView.setLayoutManager(manager);

        }
    }
}
