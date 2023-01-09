package com.codrax.tournamenthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

public class Join_Form extends AppCompatActivity {

    TextView text_top;
    TextView team_name;
    TextView category;
    EditText name;
    EditText phone;
    EditText email;
    RelativeLayout layout_img;

    ImageView icon_add;
    ImageView join_image;

    Spinner spinner1 ;
    String[] listItems1 ;

    private static final int IMAGE_REQUEST = 1;
    Uri uri;
    ProgressDialog pd;
    String imageURL;

    ImageView join_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_form);

        text_top = findViewById(R.id.text_top);
        team_name = findViewById(R.id.join_team_name);

        category = findViewById(R.id.join_category);
        name = findViewById(R.id.join_name);
        phone = findViewById(R.id.join_phone);
        email = findViewById(R.id.join_email);
        icon_add = findViewById(R.id.icon_add);
        join_image = findViewById(R.id.join_image);
        layout_img = findViewById(R.id.layout_img);
        join_btn = findViewById(R.id.join_btn);

        pd = new ProgressDialog(this);
        pd.setTitle("Wait...");
        pd.setCancelable(false);
        pd.setMessage("Test Uploading...");

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            text_top.setText(bundle.getString("type"));
            team_name.setText(bundle.getString("name"));
        }

        layout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
                icon_add.setVisibility(View.GONE);
            }
        });

        spinner1 = findViewById(R.id.test_spinner);
        String[] tests_name = {};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , tests_name);
        spinner1.setAdapter(adapter);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems1 = new String[]{
                        "Cricket - Batsman",
                        "Cricket - Bowler",
                        "Cricket - Wicket Keeper",
                        "Cricket - All Rounder",
                        "Football - Attacker",
                        "Football - Defender",
                        "Football - Goal Keeper",
                        "Football - Mid Field",
                        "Badminton - Offensive",
                        "Badminton - Defensive",
                        "Badminton - All Rounder",
                };
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Join_Form.this);
                mBuilder.setTitle("Choose an item");
                mBuilder.setSingleChoiceItems(listItems1, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        category.setText(listItems1[i]);
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_et = name.getText().toString().trim();
                String phone_et = phone.getText().toString().trim();
                String email_et = email.getText().toString().trim();
                String cat = category.getText().toString().trim();
                String category = "Player Category";

                if (name_et.isEmpty()){
                    Toast.makeText(Join_Form.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if (phone_et.isEmpty()){
                    Toast.makeText(Join_Form.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if (email_et.isEmpty()){
                    Toast.makeText(Join_Form.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if (cat.equals(category)){
                    Toast.makeText(Join_Form.this, "Choose Player Category", Toast.LENGTH_SHORT).show();
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
            join_image.setImageURI(uri);
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
            }
        });
    }

    private void uploadTest() {
        String type = text_top.getText().toString().trim();
        String name_tv = name.getText().toString().trim();
        String phone_tv = phone.getText().toString().trim();
        String email_tv = email.getText().toString().trim();
        String team_name_tv = team_name.getText().toString().trim();
        String cat_tv = category.getText().toString().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ~ hh:mm:ss aa");
        String currentDateAndTime = sdf.format(new Date());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(type+" Players").child(team_name_tv).child(currentDateAndTime);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("name", name_tv);
        hashMap.put("email", email_tv);
        hashMap.put("phone", phone_tv);
        hashMap.put("team_name", team_name_tv);
        hashMap.put("type", type);
        hashMap.put("cat", cat_tv);
        hashMap.put("image1", imageURL);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Join_Form.this, "Test Uploaded", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Join_Form.this, "Failed to Upload..." , Toast.LENGTH_SHORT).show();
            }
        });
    }

}