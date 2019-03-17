package com.gamingTournament.gamingTournament;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.MyViewHolder> {
    private OnItemClickListener listener;

    public OngoingAdapter(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ongoing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (position % 2 == 0){
            holder.imageView.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        MyViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.image);
        }
    }

    public interface OnItemClickListener{
        public void onClickListener(int position);
    }
}
