package com.example.ecom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;


public class dashboardFragment extends Fragment{
    Button button;
    View v2;
    public dashboardFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v2= inflater.inflate(R.layout.fragment_dashboard, container, false);
        return v2;

    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=(Button)view.findViewById(R.id.button);
        Log.e("button", "button "+ button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "ndndnkncdm", Toast.LENGTH_SHORT).show();
                Log.i("njfnjnfjk","samidhaaaaaaaa>>>>>>>>>>>>>>>>>");
            }
        });

    }


}
