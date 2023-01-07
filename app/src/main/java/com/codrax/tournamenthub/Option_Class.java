package com.codrax.tournamenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Option_Class extends AppCompatActivity {

    TextView text_top;
    ImageView tournament_btn , team_play_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_class);

        team_play_btn = findViewById(R.id.team_play_btn);
        tournament_btn = findViewById(R.id.tournament_btn);

        text_top = findViewById(R.id.text_top);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            text_top.setText(bundle.getString("cat"));
        }

        team_play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Option_Class.this , Ask_Desicion.class);
                String value = text_top.getText().toString().trim();
                intent.putExtra("key", value);
                startActivity(intent);
            }
        });

        tournament_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Option_Class.this, "This Feature will be Coming Soon !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}