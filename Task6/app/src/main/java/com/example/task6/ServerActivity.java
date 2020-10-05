package com.example.task6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ServerActivity extends AppCompatActivity {
    ServerThread server=null;
    TextView number1,number2,answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        initTextView();
    }
    private void initTextView(){
        number1 = (TextView)findViewById(R.id.number1);
        number2=(TextView)findViewById(R.id.number2);
        answer=(TextView)findViewById(R.id.server_answer);
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
            server  = new ServerThread(this,number1,number2,answer);
            server.start();
        }
    }
}