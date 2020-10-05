package com.example.task6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ServerActivity extends AppCompatActivity {
    ServerThread server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        server.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(server!=null){
            server.connect();
        }else{
            server  = new ServerThread(this);
            server.start();
        }
    }
}