package com.gamingTournament.gamingTournament.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gamingTournament.gamingTournament.Lists.list_transactions;
import com.gamingTournament.gamingTournament.R;

import org.w3c.dom.Text;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder>{

    List<list_transactions> transactionsList;

    public TransactionAdapter(List<list_transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public TransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_transactions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.MyViewHolder holder, int position) {
                list_transactions item = transactionsList.get(position);
                String trans_type = item.getTrans_type();
                holder.trans_type.setText(trans_type.toUpperCase());
                holder.trans_description.setText(item.getTrans_description());
                holder.dateTime.setText(item.getTime_stamp());
                holder.amount.setText("₹"+item.getAmount());

                if(trans_type.toLowerCase().equals("credit"))
                {
                    holder.trans_type.setTextColor(Color.parseColor("#2e7d32"));
                    holder.amount.setTextColor(Color.parseColor("#2e7d32"));
                    holder.amount.setText("+ ₹"+item.getAmount());
                }
                else if(trans_type.toLowerCase().equals("debit"))
                {
                    holder.trans_type.setTextColor(Color.parseColor("#d32f2f"));
                    holder.amount.setTextColor(Color.parseColor("#d32f2f"));
                    holder.amount.setText("- ₹"+item.getAmount());
                }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trans_type,trans_description,dateTime,amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            trans_type=itemView.findViewById(R.id.tr_tran_type);
            trans_description=itemView.findViewById(R.id.tr_description);
            dateTime=itemView.findViewById(R.id.tr_dateTime);
            amount=itemView.findViewById(R.id.tr_amount);

        }
    }
}
