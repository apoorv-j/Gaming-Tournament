package com.gamingTournament.gamingTournament.activity;

import android.app.Activity;
import android.os.Bundle;

import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.WinnerAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ResultDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        WinnerAdapter adapter = new WinnerAdapter();

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}
