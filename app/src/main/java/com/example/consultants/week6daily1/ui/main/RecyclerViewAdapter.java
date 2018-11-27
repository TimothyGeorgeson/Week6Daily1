package com.example.consultants.week6daily1.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.consultants.week6daily1.R;
import com.example.consultants.week6daily1.model.MyPlace;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<MyPlace> placeList;

    public RecyclerViewAdapter(List<MyPlace> placeList) {
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        MyPlace place = placeList.get(i);

        viewHolder.tvName.setText(place.getName());
        viewHolder.tvStreet.setText(place.getStreet());
        viewHolder.tvRating.setText("Rating: " + place.getRating());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvStreet;
        TextView tvRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvStreet = itemView.findViewById(R.id.tvStreet);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
