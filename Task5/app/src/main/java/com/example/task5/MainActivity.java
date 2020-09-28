package com.example.task5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText userNameRegistration, cardNumberRegistration,numberGuess;
    private TextView serverOutput;
    private ConstraintLayout registrationLayout,gameLayout;
    private Button startGameButton,sendButton, exitButton;
    private URL url;
    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        initLayouts();
        initTextViews();
        initEditText();
        initStartButton();
        initSendButton();
        initExitButton();
    }

    private void initExitButton(){
        exitButton = (Button)findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberGuess.setText("");
                gameLayout.setVisibility(ConstraintLayout.GONE);
                registrationLayout.setVisibility(ConstraintLayout.VISIBLE);
            }
        });
    }

    private void initTextViews(){
        serverOutput = (TextView)findViewById(R.id.server_speak_textView);
    }

    private void initSendButton(){
        sendButton = (Button)findViewById(R.id.send_button);
        final MainActivity self =this;
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberGuess.getText().toString();
                try {
                    url = new URL("http://tomcat.stud.iie.ntnu.no/studtomas/tallspill.jsp?tall="+number);
                }catch(MalformedURLException e){
                    System.out.println("The url can't be parsed");
                }
                System.out.println(url);
                ConnectionHandler ch = new ConnectionHandler(self,url);
                ch.start();
                try {
                    ch.join();
                }catch(InterruptedException e){
                    System.out.println("Interrupted");
                }
                System.out.println("Guess made");
                numberGuess.setText("");
                serverOutput.setText(res);
            }
        });
    }

    //Function to initiate the layouts
    private void initLayouts(){
        registrationLayout = (ConstraintLayout)findViewById(R.id.registration_layout);
        registrationLayout.setVisibility(ConstraintLayout.VISIBLE);
        gameLayout = (ConstraintLayout)findViewById(R.id.game_layout);
        gameLayout.setVisibility(ConstraintLayout.GONE);
    }

    //Function that allows a thread to set result
    public void setRes(String res){
        this.res = res;
    }

    //Function to initiate the EditText objects
    private void initEditText(){
        //EditTexts from the registrationLayout
        userNameRegistration = (EditText)findViewById(R.id.editTextUserName);
        cardNumberRegistration = (EditText)findViewById(R.id.editTextCardNumber);
        //EditText from the gameLayout
        numberGuess = (EditText)findViewById(R.id.player_input_textView);
    }

    //Function to initiate the startButton object
    private void initStartButton(){
        startGameButton = (Button)findViewById(R.id.start_button);
        final MainActivity self =this;
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                try {
                    String name = userNameRegistration.getText().toString();
                    String cardNr = cardNumberRegistration.getText().toString();
                    url = new URL("http://tomcat.stud.iie.ntnu.no/studtomas/tallspill.jsp?navn="+name+"&kortnummer="+cardNr);
                }catch(MalformedURLException e){
                    System.out.println("The url can't be parsed");
                }
                System.out.println(url);
                ConnectionHandler ch = new ConnectionHandler(self,url);
                ch.start();
                try {
                    ch.join();
                }catch(InterruptedException e){
                    System.out.println("Interrupted");
                }
                System.out.println("Main thread: child finished");
                System.out.println("Starting game!");
                serverOutput.setText(res);
                registrationLayout.setVisibility(ConstraintLayout.GONE);
                gameLayout.setVisibility(ConstraintLayout.VISIBLE);

                //if(Server gives valid result){
                //  Start gameFragment
                //}else{
                //  retry
                //}

            }
        });
    }

}