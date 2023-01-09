package com.codrax.tournamenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_Players extends AppCompatActivity {

    TextView text_top;
    TextView text_top2;

    RecyclerView mOnline_Recycler;
    ArrayList<Model_Players> modelOnlines_list;

    private DatabaseReference databaseReference;
    ProgressDialog pd;
    Adapter_Players adapter_online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_players);

        text_top = findViewById(R.id.text_top);
        text_top2 = findViewById(R.id.text_top2);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            text_top.setText(bundle.getString("name"));
            text_top2.setText(bundle.getString("type"));
        }

        pd = new ProgressDialog(this);
        pd.setTitle("Wait...");
        pd.setCancelable(false);
        pd.setMessage("Test Uploading...");

        pd.show();
        mOnline_Recycler = findViewById(R.id.recycler_catagories);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        layoutManager.setStackFromEnd(true);
        mOnline_Recycler.setLayoutManager(layoutManager);

        modelOnlines_list = new ArrayList<>();

        adapter_online = new Adapter_Players(List_Players.this , modelOnlines_list);
        mOnline_Recycler.setAdapter(adapter_online);

        String type = text_top2.getText().toString().trim();
        String name = text_top.getText().toString().trim();

        databaseReference = FirebaseDatabase.getInstance().getReference(type+" Players").child(name);
        pd.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                modelOnlines_list.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Model_Players modelOnline = itemSnapshot.getValue(Model_Players.class);
                    modelOnlines_list.add(modelOnline);
                }
                adapter_online.notifyDataSetChanged();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(List_Players.this, ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }
}