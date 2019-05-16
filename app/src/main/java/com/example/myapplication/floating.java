package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
public class floating extends AppCompatActivity {

    // Declaration
    private FloatingActionButton floatingActionButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<Object1> notes_list;
    ArrayList<Object1> edit_list;
    ArrayList<String> notes;
    ArrayList<String> edit_notes;
    ListView list;
    ArrayAdapter<String> arrayAdapter;
    String user_name;
    String j,k;
    Bundle passing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);



        notes_list = new ArrayList<Object1>();
        notes = new ArrayList<String>();
        edit_list = new ArrayList<Object1>();
        edit_notes = new ArrayList<String>();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        list = (ListView) findViewById(R.id.list_view);



        passing = getIntent().getExtras();
        if (passing != null) {
            user_name = passing.getString("Logined User");

        }

        // working of floating action button.
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notes.class);
                intent.putExtra("logined_user", user_name);
                startActivity(intent);

            }
        });


        // working whenever list item is being just clicked.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                j = list.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(), Notes.class);
                edit();
                intent.putExtra("passing", j);
                intent.putExtra("logined_user", user_name);
                startActivity(intent);
            }
        });

        // working whenever list item is being clicked for a long time.
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new MaterialAlertDialogBuilder(floating.this)
                        .setTitle("DELETE")
                        .setMessage("Do you want to delete note ?")
                        .setPositiveButton("Cancel", null)
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                j= list.getItemAtPosition(position).toString();
                                edit();
                                onResume();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    // This method updates the arrayAdapter every time , to show an updated list of notes.
    @Override
    protected void onResume() {
        super.onResume();

        int count=0;
        notes.clear();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Notes", null);
        Type type = new TypeToken<ArrayList<Object1>>(){}.getType();
        notes_list = gson.fromJson(json, type);

        if(notes_list!=null) {
        for (Object1 obj2 : notes_list) {
            if (obj2.getUser().matches(user_name))
                count = count + 1;
        }}

        if (notes_list == null && notes.isEmpty() || count==0) {
            notes.add("Add a New Note....");
        }
        else {
            for (Object1 obj1 : notes_list) {
                if (obj1.getUser().matches(user_name))
                    notes.add(obj1.getNote());
            }
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);
        list.setAdapter(arrayAdapter);

    }


    // This method is being used to delete the clicked item from the shared preference.
    public void edit() {

         notes_list.clear();
         Gson gson = new Gson();
         String json = sharedPreferences.getString("Notes", null);
         Type type = new TypeToken<ArrayList<Object1>>(){}.getType();
         edit_list = gson.fromJson(json, type);

        for (Object1 obj1 : edit_list) {
            if (obj1.getUser().matches(user_name))
                edit_notes.add(obj1.getNote());
            else
            {
                Object1 obj = new Object1();
                obj.user=obj1.getUser();
                obj.note=obj1.getNote();
                notes_list.add(obj);
            }
        }

        edit_notes.remove(j);

        if(edit_notes.isEmpty() && notes_list.isEmpty() )
        {
            editor.putString("notes",null);
        }
        else
        {
            Object1 obj = new Object1();
            for (String s : notes) {
                obj.user = user_name;
                obj.note = s;
                notes_list.add(obj);
            }
        }

        gson = new Gson();
        json = gson.toJson(notes_list);
        editor.putString("Notes", json);
        editor.commit();

    }
}





