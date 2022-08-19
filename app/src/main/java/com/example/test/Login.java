package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText email=(EditText) findViewById(R.id.Email);
        EditText password=(EditText) findViewById(R.id.Password);
        Button submit=(Button)findViewById(R.id.Loginbtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickMyButton(view,email,password);

            }
        });
    }
    public void clickMyButton(View view,EditText email,EditText password){
        if(email.getText().toString()=="a"||password.getText().toString()=="a"){
            Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_LONG).show();
        }
    }
}