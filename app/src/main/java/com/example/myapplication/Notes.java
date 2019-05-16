package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Notes extends AppCompatActivity {

    private Button btn_note;
    private TextView notes;
    SharedPreferences sharedPreferences;
    ArrayList<Object1>notes_list;
    ArrayList<Object1>notes_list1;
    SharedPreferences.Editor editor;
    String user_name;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        notes = (TextView) findViewById(R.id.txt_notes);
        btn_note = (Button) findViewById(R.id.btn_note);
        notes_list = new ArrayList<Object1>();
        notes_list1 = new ArrayList<Object1>();

        Bundle passing =getIntent().getExtras();
        if(passing!=null)
        {
            value=passing.getString("passing");
            notes.setText(value);
        }

         Bundle logined_user=getIntent().getExtras();
        {
            if(logined_user!=null)
                user_name=logined_user.getString("logined_user");
        }

        // Working of the "Save Note" Button.
        btn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = sharedPreferences.getString("Notes",null);
                Type type = new TypeToken<ArrayList<Object1>>(){}.getType();
                notes_list1 = gson.fromJson(json, type);
                if(notes_list1!=null)
                {
                    notes_list.addAll(notes_list1);
                }
                Object1 obj = new Object1();
                obj.note = notes.getText().toString();
                obj.user = user_name;
                notes_list.add(obj);

                gson = new Gson();
                json = gson.toJson(notes_list);
                editor.putString("Notes", json);
                editor.commit();
                finish();
            }
        });
    }}




