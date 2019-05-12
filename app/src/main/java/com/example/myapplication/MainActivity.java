package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


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
public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TextView sign;
    private TextView name;
    private TextView password;
    private Button login;
    public ArrayList<Object>objectList1;
    public ArrayList<Object>objectList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        sign =(TextView)findViewById(R.id.sign_up);
        name=(TextView)findViewById(R.id.name_login);
        password=(TextView)findViewById(R.id.pass_login);
        login=(Button)findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(objectList1==null)
                {

                     Snackbar snackbar3;
                     View context_View=findViewById(R.id.btn_login);
                     snackbar3=Snackbar.make(context_View,"No users registered",Snackbar.LENGTH_SHORT);
                     View snackbarView = snackbar3.getView();
                     snackbarView.setBackgroundColor(Color.RED);
                     snackbar3.show();

                }
                else
                {
                   if(name.getText().toString().matches("")||password.getText().toString().matches(""))
                   {
                         Snackbar snackbar2;
                         View context_View=findViewById(R.id.btn_login);
                         snackbar2=Snackbar.make(context_View,"User Name or Password Missing",Snackbar.LENGTH_SHORT);
                         View snackbarView = snackbar2.getView();
                         snackbarView.setBackgroundColor(Color.RED);
                         snackbar2.show();
                   }

                objectList1.addAll(objectList2);
                for(Object obj : objectList1)
                {
                    if(obj.getName()!=null && obj.getName().contains(name.getText().toString())&& obj.getPassword()!=null && obj.getPassword().contains(password.getText().toString()))
                    {
                        Snackbar snackbar;
                        View context_View=findViewById(R.id.btn_login);
                        snackbar=Snackbar.make(context_View,"User Successfully Logged In",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    else
                    {
                         Snackbar snackbar1;
                         View context_View=findViewById(R.id.btn_login);
                         snackbar1=Snackbar.make(context_View,"wrong entry",Snackbar.LENGTH_SHORT);
                         View snackbarView = snackbar1.getView();
                         snackbarView.setBackgroundColor(Color.RED);
                         snackbar1.show();
                    }

                }
            }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),sign_up.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
         Gson gson = new Gson();
         String json = sharedPreferences.getString("Values",null);
         Type type = new TypeToken<ArrayList<Object>>(){}.getType();
         objectList2 = gson.fromJson(json,type);

         if(objectList1!=null && objectList2!=null)
         {
             objectList1.addAll(objectList2);
         }
         else if(objectList1==null && objectList2!=null)
         {
             objectList1=objectList2;
         }
         else if(objectList1!=null && objectList2==null)
         {
             objectList1=objectList1;
         }


    }

}
