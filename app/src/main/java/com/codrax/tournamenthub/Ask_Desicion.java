package com.codrax.tournamenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Ask_Desicion extends AppCompatActivity {

    TextView text_top;
    ImageView create_btn , join_btn , view_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_desicion);

        create_btn = findViewById(R.id.create_btn);
        join_btn = findViewById(R.id.join_btn);
        view_btn = findViewById(R.id.view_btn);

        text_top = findViewById(R.id.text_top);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            text_top.setText(bundle.getString("key"));
        }

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ask_Desicion.this , Create_Form.class);
                String value = text_top.getText().toString().trim();
                intent.putExtra("value", value);
                startActivity(intent);
            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ask_Desicion.this , List_Team.class);
                String value = text_top.getText().toString().trim();
                intent.putExtra("value", value);
                startActivity(intent);
            }
        });

        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ask_Desicion.this , All_Teams.class);
                String value = text_top.getText().toString().trim();
                intent.putExtra("value", value);
                startActivity(intent);
            }
        });
    }
}