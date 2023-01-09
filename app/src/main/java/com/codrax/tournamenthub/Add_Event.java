package com.codrax.tournamenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Add_Event extends AppCompatActivity {

    TextView time;
    EditText event;
    ImageView create_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        time = findViewById(R.id.time);
        event = findViewById(R.id.event);
        create_btn = findViewById(R.id.create_btn);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickDialog();
            }
        });

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String event_name = event.getText().toString().trim();
                String event_time = time.getText().toString().trim();
                String event = "TIME";
                if (event_name.isEmpty()){
                    Toast.makeText(Add_Event.this, "Enter Detail of Event...", Toast.LENGTH_SHORT).show();
                }
                else if (event_time.equals(event)){
                    Toast.makeText(Add_Event.this, "Choose Date of Event...", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploaddata();
                }
            }
        });
    }

    private void uploaddata() {
        String event_text = event.getText().toString().trim();
        String time_text = time.getText().toString().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ~ hh:mm:ss aa");
        String currentDateAndTime = sdf.format(new Date());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events").child(currentDateAndTime);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("event", event_text);
        hashMap.put("time", time_text);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Add_Event.this, "Test Uploaded", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Add_Event.this, "Failed to Upload..." , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void datePickDialog()
    {

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view , int year, int monthOfYear , int dayOfMonth) {

/*
                String s=dayOfMonth+"/"+monthOfYear+"/"+year;
*/
                int m = monthOfYear+1;
                String s=dayOfMonth+"/"+m+"/"+year;

                time.setText(s);

            }

        };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        new DatePickerDialog(this, style,date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}