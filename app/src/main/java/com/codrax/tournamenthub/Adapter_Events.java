package com.codrax.tournamenthub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Events extends RecyclerView.Adapter<Adapter_Events.MyViewHolder> {

    private Context context;
    public ArrayList<Model_Events> androidArrayList, filterList;

    Adapter_Events(Context context, ArrayList<Model_Events> androidArrayList) {
        this.context = context;
        this.androidArrayList = androidArrayList;
        this.filterList = androidArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_events, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_Events modelOnline = androidArrayList.get(position);

        final String name = modelOnline.getEvent();
        final String limit = modelOnline.getTime();

        holder.name.setText(name);
        holder.time.setText(limit);

    }

    @Override
    public int getItemCount() {
        return androidArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.event_name);
            time = (TextView) itemView.findViewById(R.id.event_time);
        }
    }

}
