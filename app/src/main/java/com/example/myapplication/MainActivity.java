package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
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

    // Declaration
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TextView sign;
    private int count;
    private TextView name;
    private TextView password;
    private Button login;
    public ArrayList<Object>User_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(TextView)findViewById(R.id.name_login);
        password=(TextView)findViewById(R.id.pass_login);
        sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        User_List=new ArrayList<Object>();
        sign =(TextView)findViewById(R.id.sign_up);
        login=(Button)findViewById(R.id.btn_login);


        // Whenever Login Button is being clicked, it checks whether the details of users entered
        // in the fields matches with the user list who have already signed in or not.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(User_List==null) {

                     Snackbar snackbar3;
                     View context_View=findViewById(R.id.btn_login);
                     snackbar3=Snackbar.make(context_View,"No users registered",Snackbar.LENGTH_SHORT);
                     snackbar3.show();
                }
                else if(name.getText().toString().matches("")||password.getText().toString().matches("")) {
                         Snackbar snackbar2;
                         View context_View=findViewById(R.id.btn_login);
                         snackbar2=Snackbar.make(context_View,"User Name or Password Missing",Snackbar.LENGTH_SHORT);
                         snackbar2.show();
                }
                else {
                    for(Object obj : User_List) {
                    if(obj.getName().contains(name.getText().toString()) && obj.getPassword().contains(password.getText().toString()))
                    {
                        count = 1;
                        break;
                    }
                    else
                        count = 0;
                }
                    if(count==1)
                    {
                        Intent intent = new Intent(getApplicationContext(),floating.class);
                        intent.putExtra("Logined User",name.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Snackbar snackbar;
                        View context_View=findViewById(R.id.btn_login);
                        snackbar=Snackbar.make(context_View,"Wrong Entry",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }

            }
            }
        });

        // Sign Text View Code
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),sign_up.class);
                startActivity(intent);
            }
        });
    }


    // On start of Main Activity every time, It will get a list of users already signed in the application.
    @Override
    protected void onResume() {
        super.onResume();

         Gson gson = new Gson();
         String json = sharedPreferences.getString("Values",null);
         Type type = new TypeToken<ArrayList<Object>>(){}.getType();
         User_List = gson.fromJson(json,type);
    }
}
