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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText mEmailEt, mPassEt;
    TextView mSignInBtn;
    TextView mSignUpTv;
    TextView mForgetTv;

    ProgressDialog pd;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailEt = findViewById(R.id.email_et_login);
        mPassEt = findViewById(R.id.password_et_login);
        mSignInBtn = findViewById(R.id.submit_login_btn);
        mSignUpTv = findViewById(R.id.goto_signup);
        mForgetTv = findViewById(R.id.forget_text);

        pd = new ProgressDialog(this);
        pd.setTitle("Loading for Login...");
        pd.setCancelable(false);
        pd.setMessage("Please wait a while... Or check your Internet connection");

        mAuth = FirebaseAuth.getInstance();

        mSignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });

        mForgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Login.this, Forget_Password.class));
            }
        });

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEt.getText().toString().trim();
                String pass = mPassEt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                    Toast.makeText(Login.this , "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if (pass.isEmpty()) {
                    mPassEt.setError("Invalid Password");
                    mPassEt.setFocusable(true);
                    Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd.show();
                    signIn(email, pass);
                }
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Signed in as: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(Login.this , MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            pd.dismiss();
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();   // goto previous activity
        return super.onSupportNavigateUp();
    }
}