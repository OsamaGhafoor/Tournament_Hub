package com.codrax.tournamenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rupins.drawercardbehaviour.BuildConfig;
import com.rupins.drawercardbehaviour.CardDrawerLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    private CardDrawerLayout drawer;

    private String[] catagories_name = {
            "Cricket",
            "Football",
            "Badminton",
    };

    private int[] images1_catagories = {
            R.drawable.sample_img,
            R.drawable.sample2,
            R.drawable.sample3,
    };

    private RecyclerView catagories_recycler;
    private ArrayList<Model_Items> catagoriesArrayList;
    private Adapter_Items adapter_catagories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        drawer = (CardDrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setRadius(Gravity.START, 35);
        drawer.setViewElevation(Gravity.START, 20);

        catagories_recycler = findViewById(R.id.recycler_catagories);
        load_catagories();
    }

    private void load_catagories() {
        catagoriesArrayList = new ArrayList<>();

        for (int i = 0; i < catagories_name.length; i++) {
            Model_Items modelAndroid = new Model_Items(
                    catagories_name[i],
                    images1_catagories[i]
            );
            catagoriesArrayList.add(modelAndroid);
        }
        adapter_catagories = new Adapter_Items(this, catagoriesArrayList);
        catagories_recycler.setAdapter(adapter_catagories);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.item1){
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }
        else
        if(id==R.id.item2){
            Toast.makeText(this, "Calender", Toast.LENGTH_SHORT).show();
        }
        else
        if(id==R.id.item3){
            Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
        }
        else
        if(id==R.id.item4){
            Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
        }
        else
        if(id==R.id.item5){
            Toast.makeText(this, "Contact Us!", Toast.LENGTH_SHORT).show();
        }
        else
        if(id==R.id.item6){
            firebaseAuth.signOut();
            checkUserStatus();
            Intent intent = new Intent(MainActivity.this , Login.class);
            Toast.makeText(MainActivity.this, "Log out successfully...", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            Toast.makeText(MainActivity.this , "Logged In...", Toast.LENGTH_SHORT).show();
        }
        else{
            startActivity(new Intent(MainActivity.this , Login.class));
            finish();
        }
    }
}