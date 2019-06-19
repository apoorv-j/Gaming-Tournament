package com.gamingTournament.gamingTournament.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.Lists.list_winner;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.ViewModels.WinnerViewModel;
import com.gamingTournament.gamingTournament.activity.ResultDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LudoResultAdapter extends RecyclerView.Adapter<LudoResultAdapter.MyViewHolder> {

    private List<list_play> matchDetails;
    private List<list_winner> winnerList;
    private Fragment context;
    String URL ="http://gamingtournament.in/appimg/";

    public LudoResultAdapter(List<list_play> matchDetails,Fragment context) {
        this.matchDetails = matchDetails;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ludo_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        list_play item = matchDetails.get(position);
        if(Integer.parseInt(item.getEntryStatus())!=1)
        {

            holder.dateTime.setText(item.getDateTime());
            holder.winPrize.setText("₹"+item.getWinPrize());
            holder.entryFee.setText("₹ "+item.getEntryFee());
            Picasso.get()
                    .load(URL+"ludo.jpg")
                    .fit()
                    .into(holder.imageView);

            ViewModel winnermodel = ViewModelProviders.of(context).get(WinnerViewModel.class);
            ((WinnerViewModel) winnermodel).matchWinner(item.getMatchID(),"ludo").observe(context, new Observer<List<list_winner>>() {
                @Override
                public void onChanged(List<list_winner> list_winners) {
                    String status=list_winners.get(0).getStatus();
                    if(status==null)
                    {
                        holder.winnerName.setText(list_winners.get(0).getWinnerName());
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
        TextView matchTitle,dateTime, winPrize,entryFee, playersJoined,winnerName;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.lr_image);
            matchTitle=itemView.findViewById(R.id.lr_textView);
            dateTime=itemView.findViewById(R.id.lr_textView2);
            winPrize=itemView.findViewById(R.id.lr_textView6);
            entryFee=itemView.findViewById(R.id.lr_textView8);
            playersJoined=itemView.findViewById(R.id.lr_textView14);
            winnerName=itemView.findViewById(R.id.lr_winner_name);
        }
    }
}
