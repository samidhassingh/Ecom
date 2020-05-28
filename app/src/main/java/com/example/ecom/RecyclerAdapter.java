package com.example.ecom;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    List<String> moviesList;
    List<String> moviesListAll;

    public RecyclerAdapter(List<String> moviesList){
        this.moviesList=moviesList;
        this.moviesListAll=new ArrayList<>(moviesList);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.movieName.setText(moviesList.get(position));

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void updateList(List<String> list) {
        moviesList = list;
        notifyDataSetChanged();
    }


    public void filter(String constraint) {
        List<String> filteredList = new ArrayList<>();
        if (constraint.isEmpty()) {
            filteredList.addAll(moviesListAll);
        } else {
            for (String movie : moviesList) {

                if (movie.toLowerCase().contains(constraint.toLowerCase())) {
                    filteredList.add(movie);
                }
            }

        }
        updateList(filteredList);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName=itemView.findViewById(R.id.movieName);
        }
    }
}
