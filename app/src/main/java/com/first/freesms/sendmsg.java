package com.first.freesms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class sendmsg extends AppCompatActivity {

    Button sendButton;
    TextView balance,result;
    EditText phone,messege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmsg);

        sendButton = findViewById(R.id.sendBtn);
        balance = findViewById(R.id.balance);
        phone = findViewById(R.id.phonenumber);
        messege = findViewById(R.id.messege);
        result = findViewById(R.id.result);


        Bundle bundle = getIntent().getExtras();
        String apikey = bundle.getString("apikey");



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable phonenum = phone.getText();
                String txtmessege = messege.getText().toString();

                if(phonenum.length() == 0){
                    Toast.makeText(sendmsg.this,"Please Enter your Number",Toast.LENGTH_SHORT).show();
                }else {
                    if(phonenum.length() < 11){
                        Toast.makeText(sendmsg.this,"Invalid Number",Toast.LENGTH_SHORT).show();
                    }else {
                        if(txtmessege.length() == 0){
                            Toast.makeText(sendmsg.this,"Type a messege....",Toast.LENGTH_SHORT).show();
                        }else {
//                            Toast.makeText(sendmsg.this,apikey,Toast.LENGTH_SHORT).show();


                            // OkHttp start
                            OkHttpClient okHttpClient = new OkHttpClient();

                            String url = "https://api.sms.net.bd/user/balance/?api_key="+apikey;

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
                                        String jsonresponse = response.body().string();

                                        sendmsg.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
//                                                result.setText(jsonresponse);

                                                JSONObject obj = null;
                                                try {
                                                    obj = new JSONObject(jsonresponse);
                                                    String data = obj.getString("data");
                                                    Toast.makeText(sendmsg.this,data,Toast.LENGTH_SHORT).show();
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
                }
            }


        });

    }
}