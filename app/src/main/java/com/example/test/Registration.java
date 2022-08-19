package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText name=(EditText) findViewById(R.id.Name);
        EditText email=(EditText) findViewById(R.id.Email);
        EditText password=(EditText) findViewById(R.id.Password);
        Button submit=(Button)findViewById(R.id.Loginbtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString()==""||email.getText().toString()==""||password.getText().toString()==""){
                    Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_SHORT).show();
                }
                else{
                    //go to home window
                }
            }
        });

    }
}