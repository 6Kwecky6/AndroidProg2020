package com.example.task21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button toastBtn,d4Btn,d6Btn,d8Btn,d10Btn,d12Btn,d20Btn, d100Btn;
    Toast toast;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent();

        toastBtn = (Button) findViewById(R.id.toast_Button);
        d4Btn = (Button) findViewById(R.id.d4);
        d6Btn = (Button) findViewById(R.id.d6);
        d8Btn = (Button) findViewById(R.id.d8);
        d10Btn = (Button) findViewById(R.id.d10);
        d12Btn = (Button) findViewById(R.id.d12);
        d20Btn = (Button) findViewById(R.id.d20);
        d100Btn = (Button) findViewById(R.id.d100);

        intent.putExtra("inactive",R.id.d20);
        d20Btn.setEnabled(false);

        toastBtn.setOnClickListener(MainActivity.this);
        d4Btn.setOnClickListener(MainActivity.this);
        d6Btn.setOnClickListener(MainActivity.this);
        d8Btn.setOnClickListener(MainActivity.this);
        d10Btn.setOnClickListener(MainActivity.this);
        d12Btn.setOnClickListener(MainActivity.this);
        d20Btn.setOnClickListener(MainActivity.this);
        d100Btn.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v){
        Button activate;
        switch(v.getId()){
            case R.id.toast_Button:
                int res = ThreadLocalRandom.current().nextInt(1,intent.getIntExtra("max",20)+1);
                intent.putExtra("res",res);
                Context context = getApplicationContext();
                CharSequence toastText = "You rolled a "+ res;
                System.out.println(toastText.toString());
                setResult(RESULT_OK,intent);

                toast = Toast.makeText(context,toastText,Toast.LENGTH_SHORT);
                toast.show();
                finish();
                break;
            case R.id.d4:
                activate = (Button)findViewById(intent.getIntExtra("inactive",-1));
                activate.setEnabled(true);
                intent.putExtra("max",4);
                d4Btn.setEnabled(false);
                intent.putExtra("inactive",R.id.d4);
                break;
            case R.id.d6:
                activate = (Button)findViewById(intent.getIntExtra("inactive",-1));
                activate.setEnabled(true);
                intent.putExtra("max",6);
                intent.putExtra("max",6);
                d6Btn.setEnabled(false);
                intent.putExtra("inactive",R.id.d6);
                break;
            case R.id.d8:
                activate = (Button)findViewById(intent.getIntExtra("inactive",-1));
                activate.setEnabled(true);
                intent.putExtra("max",8);
                intent.putExtra("max",8);
                d8Btn.setEnabled(false);
                intent.putExtra("inactive",R.id.d8);
                break;

            case R.id.d10:
                activate = (Button)findViewById(intent.getIntExtra("inactive",-1));
                activate.setEnabled(true);
                intent.putExtra("max",10);
                intent.putExtra("max",10);
                d10Btn.setEnabled(false);
                intent.putExtra("inactive",R.id.d10);
                break;
            case R.id.d12:
                activate = (Button)findViewById(intent.getIntExtra("inactive",-1));
                activate.setEnabled(true);
                intent.putExtra("max",12);
                intent.putExtra("max",12);
                d12Btn.setEnabled(false);
                intent.putExtra("inactive",R.id.d12);
                break;
            case R.id.d20:
                activate = (Button)findViewById(intent.getIntExtra("inactive",-1));
                activate.setEnabled(true);
                intent.putExtra("max",20);
                intent.putExtra("max",20);
                d20Btn.setEnabled(false);
                intent.putExtra("inactive",R.id.d20);
                break;
            case R.id.d100:
                activate = (Button)findViewById(intent.getIntExtra("inactive",-1));
                activate.setEnabled(true);
                intent.putExtra("max",100);
                intent.putExtra("max",100);
                d100Btn.setEnabled(false);
                intent.putExtra("inactive",R.id.d100);
                break;
            default:
                break;
        }

    }
}