package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText regName=(EditText) findViewById(R.id.regName);
        EditText regEmail=(EditText) findViewById(R.id.regEmail);
        EditText regPassword=(EditText) findViewById(R.id.regPassword);
        Button regSubmit=(Button)findViewById(R.id.regBtn);

        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regName.getText().toString().equalsIgnoreCase("")||regEmail.getText().toString().equalsIgnoreCase("")||regPassword.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_SHORT).show();
                }
                else{
                    //go to home window
                }
            }
        });

    }
}