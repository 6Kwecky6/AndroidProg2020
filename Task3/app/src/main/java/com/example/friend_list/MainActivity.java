package com.example.friend_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView friendView;
    ArrayList<String> lastNameList, firstNameList, bDateList;
    ArrayAdapter<String> adapter;
    TextView firstNameView,lastNameView,bDateView,defaultView;
    EditText firstNameEdit,lastNameEdit,birthDayEdit;
    Button addButton, editButton,saveButton,cancelButton;
    ConstraintLayout editLayout;
    boolean editMode;
    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFriendList();
        initEditLayout();
        initAddButton();
        initEditButton();
        initSaveButton();
        initCancelButton();
    }

    private void initFriendList(){
        friendView = (ListView)findViewById(R.id.friend_list);
        firstNameList = new ArrayList<String>();
        lastNameList = new ArrayList<String>();
        bDateList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        friendView.setAdapter(adapter);
        friendView.setChoiceMode(android.widget.ListView.CHOICE_MODE_SINGLE);
        friendView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View choice,int pos,long id){
                displayFriendInfo(pos);
                selected = pos;
            }


        });

    }
    private void initEditLayout(){
        editLayout = (ConstraintLayout)findViewById(R.id.edit_layout);
        firstNameEdit = (EditText) findViewById(R.id.first_name_input);
        lastNameEdit = (EditText) findViewById(R.id.last_name_input);
        birthDayEdit = (EditText) findViewById(R.id.birth_date_input);
    }

    private void initAddButton(){
        addButton = (Button)findViewById(R.id.add_friend_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Framelayout for adding friend
                resetEditLayout();
                editLayout.setVisibility(androidx.constraintlayout.widget.ConstraintLayout.VISIBLE);
                addButton.setEnabled(false);
                editMode = false;
            }
        });
    }

    private void initEditButton(){
        editButton = (Button)findViewById(R.id.edit_friend);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillEditLayout(firstNameList.get(selected),lastNameList.get(selected),bDateList.get(selected));
                editLayout.setVisibility(androidx.constraintlayout.widget.ConstraintLayout.VISIBLE);
                addButton.setEnabled(false);
                editMode = true;
            }
        });
    }

    private void initSaveButton(){
        saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            //ArrayList<String> lastNameList, firstNameList, bDateList;
            @Override
            public void onClick(View v) {
                //Remember to check input
                if(isValidInput()) {
                    if (editMode) {
                        //setting vals to pos
                        firstNameList.set(selected, firstNameEdit.getText().toString());
                        lastNameList.set(selected, lastNameEdit.getText().toString());
                        bDateList.set(selected, birthDayEdit.getText().toString());
                        displayFriendInfo(selected);
                        closeEdit();
                    } else {
                        //appending to list
                        firstNameList.add(firstNameEdit.getText().toString());
                        lastNameList.add(lastNameEdit.getText().toString());
                        bDateList.add(birthDayEdit.getText().toString());
                        //Updating ListView
                        adapter.add(firstNameEdit.getText().toString()+lastNameEdit.getText().toString());
                        closeEdit();
                    }

                }else Toast.makeText(getApplicationContext(), "All inputs must be filled",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initCancelButton(){
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeEdit();
            }
        });
    }

    private void closeEdit(){
        editLayout.setVisibility(ConstraintLayout.GONE);
        addButton.setEnabled(true);
    }
    private boolean isValidInput(){
        return !(firstNameEdit.getText().toString().isEmpty()||lastNameEdit.getText().toString().isEmpty()||birthDayEdit.getText().toString().isEmpty());
    }

    private void fillEditLayout(String firstName,String lastName,String birthDate){
        firstNameEdit.setText(firstName);
        lastNameEdit.setText(lastName);
        birthDayEdit.setText(birthDate);
    }

    private void resetEditLayout(){
        firstNameEdit.setText(null);
        lastNameEdit.setText(null);
        birthDayEdit.setText(null);
    }

    private void displayFriendInfo(int pos){
        defaultView = (TextView)findViewById(R.id.default_text);
        defaultView.setVisibility(android.widget.TextView.INVISIBLE);
        firstNameView = (TextView)findViewById(R.id.first_name);
        firstNameView.setText(firstNameList.get(pos));
        firstNameView.setVisibility(android.widget.TextView.VISIBLE);
        lastNameView = (TextView)findViewById(R.id.last_name);
        lastNameView.setText(lastNameList.get(pos));
        lastNameView.setVisibility(android.widget.TextView.VISIBLE);
        bDateView = (TextView)findViewById(R.id.birth_date);
        bDateView.setText(bDateList.get(pos));
        bDateView.setVisibility(android.widget.TextView.VISIBLE);
        TextView name = (TextView)findViewById(R.id.name);
        name.setVisibility(android.widget.TextView.VISIBLE);
        TextView bDate = (TextView)findViewById(R.id.bDate);
        bDate.setVisibility(android.widget.TextView.VISIBLE);
        editButton.setVisibility(Button.VISIBLE);
    }


}