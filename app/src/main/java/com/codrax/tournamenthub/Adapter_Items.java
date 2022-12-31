package com.codrax.tournamenthub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Items extends RecyclerView.Adapter<Adapter_Items.HolderAndroid> {

    private Context context;
    private ArrayList<Model_Items> androidArrayList;

    public Adapter_Items(Context context, ArrayList<Model_Items> androidArrayList) {
        this.context = context;
        this.androidArrayList = androidArrayList;
    }

    @NonNull
    @Override
    public HolderAndroid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new HolderAndroid(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAndroid holder, int position) {
        Model_Items modelAndroid = androidArrayList.get(position);

        String heading_tv = modelAndroid.getName();
        int image_1 = modelAndroid.getImage1();

        holder.name.setText(heading_tv);
        holder.image1.setImageResource(image_1);

        /*holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , List_News.class);
                intent.putExtra("cat" , androidArrayList.get(holder.getAdapterPosition()).getName());
                context.startActivity(intent);
                Animatoo.animateZoom(context);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return androidArrayList.size();
    }

    class HolderAndroid extends RecyclerView.ViewHolder {

        private ImageView image1;
        private TextView name;

        HolderAndroid(@NonNull View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image_catagory);
            name = itemView.findViewById(R.id.name_catagory);
        }
    }
}
