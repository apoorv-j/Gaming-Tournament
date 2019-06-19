package com.gamingTournament.gamingTournament.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.R;

public class PaymentPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        Intent intent = getIntent();
        String[] details = intent.getStringArrayExtra("details");

        TextView title,type,version,map,dateTime,roomId,roomPass;

        title=findViewById(R.id.title);
        type=findViewById(R.id.type);
        version=findViewById(R.id.version);
        map=findViewById(R.id.map);
        dateTime=findViewById(R.id.dateTime);
        roomId=findViewById(R.id.roomID);
        roomPass=findViewById(R.id.roomPass);

        title.setText(details[1]);
        type.setText("Type: "+details[2]);
        version.setText("Version: "+details[3]);
        map.setText("Map: "+details[4]);
        dateTime.setText("Schedule: "+details[5]);
        roomId.setText("Room ID: "+details[6]);
        roomPass.setText("Room Password: "+details[7]);
    }
}
