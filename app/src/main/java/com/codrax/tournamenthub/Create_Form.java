package com.codrax.tournamenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Create_Form extends AppCompatActivity {

    TextView text_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        text_top = findViewById(R.id.text_top);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            text_top.setText(bundle.getString("value"));
        }
    }
}