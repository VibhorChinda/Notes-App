package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class sign_up extends AppCompatActivity {

    //Declaration
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TextView name;
    private TextView password;
    private TextView email;
    private Button sign_up;
    ArrayList<Object> objectList;
    ArrayList<Object> objectList1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        objectList = new ArrayList<Object>();
        objectList1 = new ArrayList<Object>();
        sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email=(TextView)findViewById(R.id.mail_sign);
        name=(TextView)findViewById(R.id.name_sign);
        password=(TextView)findViewById(R.id.pass_sign);
        sign_up=(Button)findViewById(R.id.btn_sign);


        // Whenever it is being clicked, it adds the new user details to shared preference with tag of values
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().matches("") || name.getText().toString().matches("") || password.getText().toString().matches("")) {
                        View context_view = findViewById(R.id.btn_sign);
                        Snackbar snackbar = Snackbar.make(context_view, "Fill The Missing Fields", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                else {
                        Object obj = new Object();
                        obj.Name = name.getText().toString();
                        obj.Password = password.getText().toString();
                        objectList.add(obj);
                        Gson gson = new Gson();
                        String json = gson.toJson(objectList);
                        editor.putString("Values", json);
                        editor.commit();
                        finish();
                }
            }
        }
        );
    }

    // Every time when this activity is created,this function gets the list of users already registered in an array list named objectlist1
    @Override
    protected void onResume() {
        super.onResume();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("Values",null);
        Type type = new TypeToken<ArrayList<Object>>(){}.getType();
        objectList1 = gson.fromJson(json, type);
        if(objectList1!=null)
        {
            objectList.addAll(objectList1);
        }
    }
}




