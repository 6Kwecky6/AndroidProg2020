package com.example.task21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener{
    TextView res_text;
    Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        res_text = findViewById(R.id.result);
        back_button = findViewById(R.id.back_button);
        startActivityForResult(new Intent(this,MainActivity.class),1);
        back_button.setOnClickListener(DisplayActivity.this);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == 1 && resultCode == RESULT_OK){
            int res =data.getIntExtra("res",0);
            System.out.println(res_text);
            res_text.setText(String.valueOf(res));
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onClick(View data){
        startActivityForResult(new Intent(this,MainActivity.class),1);
    }
}