package com.gamingTournament.gamingTournament.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.R;
import com.squareup.picasso.Picasso;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder>{
    OnItemClickListener clickListener;
    String URL ="http://gamingtournament.in/appimg/";

    public MainAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        switch (position)
        {
            case 0: holder.textView.setText("PUBG MOBILE");
                Picasso.get()
                        .load(URL+"pubg.jpg")
                        .fit()
                        .into(holder.imageView);
                    break;
            case 1: holder.textView.setText("FREE FIRE");
                Picasso.get()
                        .load(URL+"freefire.jpg")
                        .fit()
                        .into(holder.imageView);
                break;
            case 2: holder.textView.setText("MINI MILITIA");
                Picasso.get()
                        .load(URL+"minim.jpg")
                        .fit()
                        .into(holder.imageView);
                break;
            case 3: holder.textView.setText("LUDO KING");
                Picasso.get()
                        .load(URL+"ludo.jpg")
                        .fit()
                        .into(holder.imageView);
                break;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCardClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.main_card_view);
            imageView=itemView.findViewById(R.id.media_image);
            textView=itemView.findViewById(R.id.supporting_text);
        }
    }

    public interface OnItemClickListener{

        void onCardClick(int position);
    }
}
