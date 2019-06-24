package com.gamingTournament.gamingTournament.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.Adapter.TransactionAdapter;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.Lists.list_transactions;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.SharedPrefManager;
import com.gamingTournament.gamingTournament.ViewModels.TransactionViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {

    RecyclerView recyclerView;
    TransactionAdapter adapter;
    List<list_transactions> transactionsList;

    public TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        recyclerView = view.findViewById(R.id.tr_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();

        Users user = SharedPrefManager.getInstance(getActivity()).getUser();

        TransactionViewModel model = ViewModelProviders.of(TransactionFragment.this).get(TransactionViewModel.class);
        model.getTransactions(getContext(),user.getUname()).observe(TransactionFragment.this, new Observer<List<list_transactions>>() {
            @Override
            public void onChanged(List<list_transactions> list_transactions) {
                transactionsList = list_transactions;
                String status=transactionsList.get(0).getStatus();
                if(status==null) {
                    adapter=new TransactionAdapter(transactionsList);
                    recyclerView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getContext(), "No Transactions Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
