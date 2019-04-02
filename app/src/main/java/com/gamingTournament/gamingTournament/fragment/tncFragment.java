package com.gamingTournament.gamingTournament.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamingTournament.gamingTournament.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class tncFragment extends Fragment {


    public tncFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tnc, container, false);
        TextView textView = view.findViewById(R.id.tnc_text);
        textView.setText(R.string.tnc_text);
        return view;
    }

}
