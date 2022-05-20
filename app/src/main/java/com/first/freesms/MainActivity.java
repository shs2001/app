package com.first.freesms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button getStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStart = findViewById(R.id.getStart);

        getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,getapi.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(MainActivity.this);
        alertbuilder.setIcon(R.drawable.info);
        alertbuilder.setTitle("Confirm");
        alertbuilder.setMessage("Are you sure to exit?");
        alertbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
            }
        });
        alertbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertbuilder.setCancelable(false);
        AlertDialog alertDialog = alertbuilder.create();
        alertDialog.show();
    }
}