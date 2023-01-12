package com.aniu.indicator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("sss","dddd");
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView viewById = (TextView) root.findViewById(R.id.textview1);
        String key = getArguments().getString("key");
        viewById.setText(key);
        return root;
    }

}
