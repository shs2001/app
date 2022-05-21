package com.first.freesms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getapi extends AppCompatActivity {

    Button getApiButton;
    EditText apikey;
    TextView needapikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getapi);

        getApiButton = findViewById(R.id.Apibutton);
        apikey = findViewById(R.id.getApi);
        needapikey = findViewById(R.id.needapikey);

        getApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String api = apikey.getText().toString();
                if(api.length() < 1){
                    Toast.makeText(getapi.this,"Please Enter Api Key!",Toast.LENGTH_SHORT).show();
                }
                else {
                    // OkHttp start
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = "https://api.sms.net.bd/user/balance/?api_key="+api;
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String serverdata = response.body().string();
                                getapi.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        JSONObject obj = null;
                                        try {
                                            obj = new JSONObject(serverdata);
                                            String err = obj.getString("error");
                                            int error = Integer.parseInt(err);
                                            if(error == 405){
                                                Toast.makeText(getapi.this,"Invalid api key",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Intent intent = new Intent(getapi.this,sendmsg.class);
                                                intent.putExtra("apikey",api);
                                                startActivity(intent);

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }



                                    }
                                });
                            }
                        }
                    });
                    //Okhttp Stop
                }
            }
        });
        needapikey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://github.com/shs2001/sms#api-keys"));
                startActivity(browserIntent);
            }
        });
    }
}