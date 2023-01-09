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

public class Adapter_Team extends RecyclerView.Adapter<Adapter_Team.MyViewHolder> {

    private Context context;
    public ArrayList<Model_Team> androidArrayList, filterList;

    Adapter_Team(Context context, ArrayList<Model_Team> androidArrayList) {
        this.context = context;
        this.androidArrayList = androidArrayList;
        this.filterList = androidArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_teams, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_Team modelOnline = androidArrayList.get(position);

        final String name = modelOnline.getName();
        final String limit = modelOnline.getLimit();
        final String type = modelOnline.getType();
        Glide.with(context).load(androidArrayList.get(position).getImage1()).placeholder(R.drawable.logo_tournament).into(holder.image);

        holder.name.setText(name);
        holder.limit.setText(limit);
        holder.type.setText(type);

        holder.more_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , Join_Form.class);
                intent.putExtra("name" , androidArrayList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("type" , androidArrayList.get(holder.getAdapterPosition()).getType());
                context.startActivities(new Intent[]{intent});
            }
        });

    }

    @Override
    public int getItemCount() {
        return androidArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        TextView limit;
        TextView type;
        ImageView image;
        CardView more_card;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.team_name);
            limit = (TextView) itemView.findViewById(R.id.team_player);
            type = (TextView) itemView.findViewById(R.id.type_team);
            image = (ImageView) itemView.findViewById(R.id.team_img);
            more_card = (CardView) itemView.findViewById(R.id.more_card);
        }
    }

}