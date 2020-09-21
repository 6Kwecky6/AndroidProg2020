package com.example.task22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView firstNum,secondNum, uppLimit;
    Button addBtn,multBtn;
    EditText usrAnsw;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstNum = findViewById(R.id.first_num);
        secondNum = findViewById(R.id.second_num);
        addBtn = findViewById(R.id.add_btn);
        multBtn = findViewById(R.id.multiplication_btn);
        usrAnsw = findViewById(R.id.given_answer_field);
        uppLimit = findViewById(R.id.upper_limit_field);

        addBtn.setOnClickListener(MainActivity.this);
        multBtn.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View btn){
        CharSequence resText;
        Context context;

        switch (btn.getId()){
            case R.id.add_btn:
                context = getApplicationContext();
                if(Integer.parseInt(firstNum.getText().toString())+Integer.parseInt(secondNum.getText().toString())==Integer.parseInt(usrAnsw.getText().toString()))
                    resText = getString(R.string.correct);
                else resText = getString(R.string.fail)+usrAnsw.getText().toString();
                toast = Toast.makeText(context,resText,Toast.LENGTH_SHORT);
                toast.show();
                startActivityForResult(new Intent("com.example.task21.MainActivity"),1);
                break;

            case R.id.multiplication_btn:
                context = getApplicationContext();
                if(Integer.parseInt(firstNum.getText().toString())*Integer.parseInt(secondNum.getText().toString())==Integer.parseInt(usrAnsw.getText().toString()))
                    resText = getString(R.string.correct);
                else resText = getString(R.string.fail)+Integer.parseInt(firstNum.getText().toString())*Integer.parseInt(secondNum.getText().toString());
                toast = Toast.makeText(context,resText,Toast.LENGTH_SHORT);
                toast.show();
                startActivityForResult(new Intent("com.example.task21.MainActivity"),1);
                break;

            default:

        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == 1 && resultCode == RESULT_OK){
            int max= data.getIntExtra("max",0);
            uppLimit.setText(String.valueOf(max));
            firstNum.setText(String.valueOf(data.getIntExtra("res",-1)));
            secondNum.setText(String.valueOf(ThreadLocalRandom.current().nextInt(1,max+1)));
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
}