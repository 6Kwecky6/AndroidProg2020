package com.example.task6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button serverButton,clientButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initServerButton();
        initClientButton();
    }

    private void initServerButton(){
        serverButton = (Button)findViewById(R.id.server_selection_button);
        final Intent intent = new Intent(this,ServerActivity.class);
        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Starting server activity");
                startActivity(intent);
            }
        });
    }

    private void initClientButton(){
        clientButton =(Button)findViewById(R.id.client_selection_button);
        final Intent intent = new Intent(this,ClientActivity.class);
        clientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Starting client activity");
                startActivity(intent);
            }
        });
    }
}