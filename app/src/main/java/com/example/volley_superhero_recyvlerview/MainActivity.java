package com.example.volley_superhero_recyvlerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



    }
    public void liste(View view){
        Intent intent=new Intent(MainActivity.this, ListeLieux.class);
        startActivity(intent);
    }
    public void maps(View view){
        Intent intent=new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

}