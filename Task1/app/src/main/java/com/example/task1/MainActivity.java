package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int fnCount = -1;
    int lnCount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button fnButton = findViewById(R.id.fnButton);
        final Button lnButton = findViewById(R.id.lnButton);
        Resources res = getResources();
        fnCount = Integer.parseInt(res.getString(R.string.fnCount));
        lnCount = Integer.parseInt(res.getString(R.string.lnCount));

        fnButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                fnCount++;
                final TextView fnCountStr = (TextView) findViewById(R.id.fnCount);
                fnCountStr.setText(Integer.toString(fnCount));
                System.out.format("First name clicked!%nFirst name has been clicked %d times now.%n",fnCount);
            }
        });

        lnButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                lnCount++;
                final TextView lnCountStr = (TextView) findViewById(R.id.lnCount);
                lnCountStr.setText(Integer.toString(lnCount));
                System.out.format("Last name clicked!%nLast name has been clicked %d times now.%n",lnCount);
            }
        });
    }
}