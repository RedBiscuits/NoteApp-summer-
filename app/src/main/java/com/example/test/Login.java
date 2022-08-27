package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView notes = (TextView) findViewById(R.id.notesLogin);
        EditText loginEmail=(EditText) findViewById(R.id.loginEmail);
        EditText loginPassword=(EditText) findViewById(R.id.loginPassword);
        Button loginSubmit=(Button)findViewById(R.id.loginBtn);

        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginEmail.getText().toString().equalsIgnoreCase("a")||loginPassword.getText().toString().equalsIgnoreCase("a")){
                    notes.setText("Hello World");
                    Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}