package com.example.ecom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;

import com.google.android.gms.common.data.DataHolder;


import java.util.ArrayList;
import java.util.List;


public class isellFragment extends Fragment{
    View vIsell;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<String> moviesList;
    EditText searchEditText;
    Button scanQRButton;

    public isellFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vIsell = inflater.inflate(R.layout.fragment_isell, container, false);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return vIsell;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);
        //recyclerView.setHasFixedSize(true);
        searchEditText = (EditText) view.findViewById(R.id.searchEditText);
        scanQRButton=(Button)view.findViewById(R.id.scanQRButton);
        scanQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getContext(),scanCode.class));
            }
        });

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(vIsell.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        moviesList.add("Iron Man");
        moviesList.add("Hulk");
        moviesList.add("THOR");
        moviesList.add("Narnia");
        moviesList.add("Tamasha");

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // recyclerAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
               recyclerAdapter.filter(s.toString());

            }
        });


    }


}

