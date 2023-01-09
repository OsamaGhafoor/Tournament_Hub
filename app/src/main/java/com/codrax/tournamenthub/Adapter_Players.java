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

public class Adapter_Players extends RecyclerView.Adapter<Adapter_Players.MyViewHolder> {

    private Context context;
    public ArrayList<Model_Players> androidArrayList, filterList;

    Adapter_Players(Context context, ArrayList<Model_Players> androidArrayList) {
        this.context = context;
        this.androidArrayList = androidArrayList;
        this.filterList = androidArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_players, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_Players modelOnline = androidArrayList.get(position);

        final String name = modelOnline.getName();
        final String email = modelOnline.getEmail();
        final String phone = modelOnline.getPhone();
        final String type = modelOnline.getType();
        final String cat = modelOnline.getCat();
        final String team_name = modelOnline.getTeam_name();
        Glide.with(context).load(androidArrayList.get(position).getImage1()).placeholder(R.drawable.logo_tournament).into(holder.image);

        holder.name.setText(name);
        holder.email.setText(email);
        holder.phone.setText(phone);
        holder.type.setText(type);
        holder.cat.setText(cat);
        holder.team_name.setText(team_name);
    }

    @Override
    public int getItemCount() {
        return androidArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        TextView email;
        TextView phone;
        TextView type;
        TextView cat;
        TextView team_name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name_player);
            email = (TextView) itemView.findViewById(R.id.email_player);
            phone = (TextView) itemView.findViewById(R.id.phone_player);
            type = (TextView) itemView.findViewById(R.id.type_player);
            cat = (TextView) itemView.findViewById(R.id.category_player);
            team_name = (TextView) itemView.findViewById(R.id.team_name_player);
            image = (ImageView) itemView.findViewById(R.id.player_img);
        }
    }

}
