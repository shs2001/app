package com.first.freesms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class sendmsg extends AppCompatActivity {

    Button sendButton;
    TextView showbalance;
    EditText phone, messege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmsg);

        sendButton = findViewById(R.id.sendBtn);
        showbalance = findViewById(R.id.balance);
        phone = findViewById(R.id.phonenumber);
        messege = findViewById(R.id.messege);


        Bundle bundle = getIntent().getExtras();
        String apikey = bundle.getString("apikey");


        ViewBalance();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable phonenum = phone.getText();
                Editable txtmessege = messege.getText();
                if (phonenum.length() == 0) {
                    Toast.makeText(sendmsg.this, "Please Enter your Number", Toast.LENGTH_SHORT).show();
                } else {
                    if (phonenum.length() < 11) {
                        Toast.makeText(sendmsg.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                    } else {
                        if (txtmessege.length() == 0) {
                            Toast.makeText(sendmsg.this, "Type a messege....", Toast.LENGTH_SHORT).show();
                        } else {
                            sendButton.setEnabled(false);
                            sendButton.setText("Sending...");
                            // OkHttp start
                            OkHttpClient okHttpClient = new OkHttpClient();
                            String url = "https://api.sms.net.bd/sendsms?api_key="+apikey+"&msg="+txtmessege+"&to="+phonenum;
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
                                        sendmsg.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                JSONObject obj = null;
                                                try {
                                                    obj = new JSONObject(serverdata);
                                                    String err = obj.getString("error");
                                                    int error = Integer.parseInt(err);
//                                                    int error = 0;
                                                    if(error == 0){
                                                        Toast.makeText(sendmsg.this,"Messege Send Successfull",Toast.LENGTH_LONG).show();
                                                        ViewBalance();
                                                        txtmessege.clear();
                                                        sendButton.setEnabled(true);
                                                        sendButton.setText("Send Messege");
                                                    }
                                                    else if(error == 417){
                                                        Toast.makeText(sendmsg.this,"Insufficient balance",Toast.LENGTH_SHORT).show();
                                                        sendButton.setEnabled(true);
                                                        sendButton.setText("Send Messege");
                                                    }
                                                    else {
                                                        phonenum.clear();
                                                        txtmessege.clear();
                                                        Toast.makeText(sendmsg.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                                                        sendButton.setEnabled(true);
                                                        sendButton.setText("Send Messege");
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    sendButton.setEnabled(true);
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

    public void ViewBalance(){

        Bundle bundle = getIntent().getExtras();
        String apikey = bundle.getString("apikey");

        // OkHttp start
        OkHttpClient okHttpClient = new OkHttpClient();

        String url = "https://api.sms.net.bd/user/balance/?api_key=" + apikey;

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
                if (response.isSuccessful()) {
                    String jsonresponse = response.body().string();

                    sendmsg.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(jsonresponse);
                                String data = obj.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String balance = jsonObject.getString("balance");
                                showbalance.setText("Balance: "+balance+" TK");

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