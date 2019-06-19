package com.gamingTournament.gamingTournament.Adapter;

import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.Lists.list_events;
import com.gamingTournament.gamingTournament.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<list_events> eventsList;
    private OnItemClickListener listener;

    public EventAdapter(List<list_events> eventsList, OnItemClickListener listener) {
        this.eventsList = eventsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            list_events item = eventsList.get(position);
            holder.subtext.setText(item.getTextData());
            Picasso.get()
                .load("http://"+item.getImageURL())
                .resize(160, 160)
                .centerCrop()
                .into(holder.imageView);
            String link = "<a href='http://"+item.getLink()+"'> Click Here</a>";
            holder.button.setClickable(true);
            holder.button.setMovementMethod(LinkMovementMethod.getInstance());
        if (Build.VERSION.SDK_INT >= 24) {
            holder.button.setText(Html.fromHtml(link, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.button.setText(Html.fromHtml(link));
        }
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,subtext;
        ImageView imageView;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.primary_text);
            subtext=itemView.findViewById(R.id.sub_text);
            imageView=itemView.findViewById(R.id.media_image);
            button=itemView.findViewById(R.id.link_button);
        }
    }
    public interface OnItemClickListener{
        public void LinkClick(int position);

    }
}
