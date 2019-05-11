package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign =(TextView)findViewById(R.id.sign_up);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),sign_up.class);
                startActivity(intent);
            }
        });
    }
}
