package com.codrax.tournamenthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Create_Form extends AppCompatActivity {

    RelativeLayout layout_img;
    ImageView create_image;
    TextView text_top;
    ImageView icon_add;
    EditText team_name;
    ImageView create_btn;
    TextView limit;
    ImageView decrement , increment;

    private static final int IMAGE_REQUEST = 1;
    Uri uri;
    ProgressDialog pd;
    String imageURL;

    private int couter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        text_top = findViewById(R.id.text_top);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            text_top.setText(bundle.getString("value"));
        }

        pd = new ProgressDialog(this);
        pd.setTitle("Wait...");
        pd.setCancelable(false);
        pd.setMessage("Test Uploading...");

        layout_img = findViewById(R.id.layout_img);
        create_image = findViewById(R.id.create_image);
        create_btn = findViewById(R.id.create_btn);
        icon_add = findViewById(R.id.icon_add);
        team_name = findViewById(R.id.team_name);

        limit = findViewById(R.id.limit);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);

        layout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
                icon_add.setVisibility(View.GONE);
            }
        });

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (couter >= 0 && couter <= 14){
                    couter = couter + 1 ;
                    String limit_text = Integer.toString(couter);
                    limit.setText(limit_text);
                }
                else {
                    if (couter >= 1 && couter <= 15){
                        String limit_text = Integer.toString(couter);
                        limit.setText(limit_text);
                    }
                    Toast.makeText(Create_Form.this, "Choose only from 1-15", Toast.LENGTH_SHORT).show();
                }
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (couter >= 2 && couter <= 15){
                    couter = couter - 1 ;
                    String limit_text = Integer.toString(couter);
                    limit.setText(limit_text);
                }
                else {
                    if (couter >= 1 && couter <= 15){
                        String limit_text = Integer.toString(couter);
                        limit.setText(limit_text);
                    }
                    Toast.makeText(Create_Form.this, "Choose only from 1-15", Toast.LENGTH_SHORT).show();
                }
            }
        });

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_team = team_name.getText().toString().trim();
                if (name_team.isEmpty()){
                    Toast.makeText(Create_Form.this, "Enter Name of Team", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadImage();
                }
            }
        });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            uri = data.getData();
            create_image.setImageURI(uri);
        }
        else {
            Toast.makeText(this, "No Image is Selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadImage(){
        pd.show();
        String category = text_top.getText().toString().trim();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("News_Images").child(category).child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uriImage = uriTask.getResult();
                imageURL = uriImage.toString();
                uploadTest();
                uploadNoti();
            }
        });
    }

    private void uploadNoti() {
        String name = team_name.getText().toString().trim()+" - new Team Created in Tournament Hub.";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ~ hh:mm:ss aa");
        String currentDateAndTime = sdf.format(new Date());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notification").child(currentDateAndTime);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("name", name);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Create_Form.this, "Test Uploaded", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Create_Form.this, "Failed to Upload..." , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadTest() {
        String type = text_top.getText().toString().trim();
        String name = team_name.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(type).child(name);
        HashMap<String, String> hashMap = new HashMap<>();

        String limi = limit.getText().toString().trim();

        hashMap.put("limit", limi);
        hashMap.put("name", name);
        hashMap.put("type", type);
        hashMap.put("image1", imageURL);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Create_Form.this, "Test Uploaded", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Create_Form.this, "Failed to Upload..." , Toast.LENGTH_SHORT).show();
            }
        });
    }

}