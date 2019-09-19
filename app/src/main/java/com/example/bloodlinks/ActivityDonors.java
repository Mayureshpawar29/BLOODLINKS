package com.example.bloodlinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ActivityDonors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);

        Intent i=getIntent();
        Toast.makeText(this, i.getStringExtra("bg"), Toast.LENGTH_SHORT).show();
    }
}
