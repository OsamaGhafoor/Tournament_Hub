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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter_All_Teams extends RecyclerView.Adapter<Adapter_All_Teams.MyViewHolder> {

    private Context context;
    public ArrayList<Model_Team> androidArrayList, filterList;

    Adapter_All_Teams(Context context, ArrayList<Model_Team> androidArrayList) {
        this.context = context;
        this.androidArrayList = androidArrayList;
        this.filterList = androidArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_all_teams, parent, false);
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
                holder.options.setVisibility(View.VISIBLE);
            }
        });

        holder.player_list_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , List_Players.class);
                intent.putExtra("name" , androidArrayList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("type" , androidArrayList.get(holder.getAdapterPosition()).getType());
                context.startActivities(new Intent[]{intent});
            }
        });

        holder.request_match_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.request_match_tv.setText("Request is Submitted");
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
        LinearLayout options;
        TextView player_list_tv;
        TextView request_match_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.team_name);
            limit = (TextView) itemView.findViewById(R.id.team_player);
            type = (TextView) itemView.findViewById(R.id.type_team);
            image = (ImageView) itemView.findViewById(R.id.team_img);
            more_card = (CardView) itemView.findViewById(R.id.more_card);
            options = (LinearLayout) itemView.findViewById(R.id.layout_options);
            player_list_tv = (TextView) itemView.findViewById(R.id.player_list_tv);
            request_match_tv = (TextView) itemView.findViewById(R.id.request_match_tv);
        }
    }

}
