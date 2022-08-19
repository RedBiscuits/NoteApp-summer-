package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Button registration=(Button)findViewById(R.id.Loginbtn);
        Button login=(Button)findViewById(R.id.Login);
        registration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( getApplicationContext(),Registration.class);
                startActivity(myIntent);
            }
        });
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( getApplicationContext(),Login.class);
                startActivity(myIntent);
            }
        });

    }

}