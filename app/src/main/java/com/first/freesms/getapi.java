package com.first.freesms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class getapi extends AppCompatActivity {

    Button getApiButton;
    EditText apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getapi);

        getApiButton = findViewById(R.id.Apibutton);
        apikey = findViewById(R.id.getApi);

        getApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String api = apikey.getText().toString();
                if(api.length() < 1){
                    Toast.makeText(getapi.this,"Please Enter Api Key!",Toast.LENGTH_SHORT).show();
                }
                else if(api.length() < 40){
                    Toast.makeText(getapi.this,"Api key too short!",Toast.LENGTH_SHORT).show();
                }
                else if(api.length() == 40){
                    Intent intent = new Intent(getapi.this,sendmsg.class);
                    intent.putExtra("apikey",api);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getapi.this,"Invalid Api Key",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}