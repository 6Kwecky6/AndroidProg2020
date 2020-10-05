package com.example.task6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClientActivity extends AppCompatActivity {
    EditText input_number1,input_number2;
    TextView answer;
    Button sendButton;
    ClientThread client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        client = new ClientThread(this);
        initEditText();
        initTextView();
        initSendButton();
        client.start();
    }

    private void initSendButton(){
        sendButton=(Button)findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = input_number1.getText().toString()+" "+input_number2.getText().toString();
                client.send(input);
                input_number1.setText("");
                input_number2.setText("");
            }
        });
    }

    private void initEditText(){
        input_number1 = (EditText)findViewById(R.id.number1_inp);
        input_number2=(EditText)findViewById(R.id.number2_inp);
    }

    private void initTextView(){
        answer = (TextView)findViewById(R.id.answer);
    }
}