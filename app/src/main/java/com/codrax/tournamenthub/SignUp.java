package com.codrax.tournamenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {


    EditText mEmail, mPassword, mConfirmPass, nName;
    TextView gotoSignIn;
    TextView mRegisterBtn;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = findViewById(R.id.email_et);
        mPassword = findViewById(R.id.password_et);
        mConfirmPass = findViewById(R.id.confirm_password_et);
        nName = findViewById(R.id.name_et);
        mRegisterBtn = findViewById(R.id.Register_btn);
        gotoSignIn = findViewById(R.id.goto_signin);

        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this , Login.class);
                startActivity(intent);
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading for Registration...");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait a while... Or check your Internet connection");

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String cnfrm_password = mConfirmPass.getText().toString().trim();
                String name = nName.getText().toString().trim();

                if (name.isEmpty()) {
                    nName.setError("Enter User Name");
                    nName.setFocusable(true);
                    Toast.makeText(SignUp.this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Invalid Email");
                    mEmail.setFocusable(true);
                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if(email.contains("@codrax.com")){
                    mEmail.setError("Invalid Email");
                    mEmail.setFocusable(true);
                    Toast.makeText(SignUp.this, "Try any other domain rather @codrax.com", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    mPassword.setError("Password Length must be more than 6 charachters");
                    mPassword.setFocusable(true);
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!cnfrm_password.equals(password)) {
                    mConfirmPass.setError("Password doesn't match");
                    Toast.makeText(SignUp.this, "Password doesn't match...", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    register(name, email, password);
                }

            }
        });
    }

    private void register(final String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("password", password);
                            hashMap.put("email", email);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("bio", "");
                            hashMap.put("search", username.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();   // goto previous activity
        return super.onSupportNavigateUp();
    }
}