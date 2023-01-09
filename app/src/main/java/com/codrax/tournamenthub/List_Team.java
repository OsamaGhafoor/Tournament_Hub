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

public class List_Team extends AppCompatActivity {

    TextView text_top;

    RecyclerView mOnline_Recycler;
    ArrayList<Model_Team> modelOnlines_list;

    private DatabaseReference databaseReference;
    ProgressDialog pd;
    Adapter_Team adapter_online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_team);

        text_top = findViewById(R.id.text_top);

        text_top = findViewById(R.id.text_top);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            text_top.setText(bundle.getString("value"));
        }

        pd = new ProgressDialog(this);
        pd.setTitle("Wait...");
        pd.setCancelable(false);
        pd.setMessage("Test Uploading...");

        pd.show();
        mOnline_Recycler = findViewById(R.id.recycler_catagories);
        String category = text_top.getText().toString().trim();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        layoutManager.setStackFromEnd(true);
        mOnline_Recycler.setLayoutManager(layoutManager);

        modelOnlines_list = new ArrayList<>();

        adapter_online = new Adapter_Team(List_Team.this , modelOnlines_list);
        mOnline_Recycler.setAdapter(adapter_online);

        String ref = text_top.getText().toString().trim();

        databaseReference = FirebaseDatabase.getInstance().getReference(ref);
        pd.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                modelOnlines_list.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Model_Team modelOnline = itemSnapshot.getValue(Model_Team.class);
                    modelOnlines_list.add(modelOnline);
                }
                adapter_online.notifyDataSetChanged();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(List_Team.this, ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });


    }
}