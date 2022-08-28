package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Controller.DatabaseHandler;
import com.example.test.Model.User;

public class MainWindow extends AppCompatActivity {
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        Button registration=(Button)findViewById(R.id.mainRegBtn);
        Button login=(Button)findViewById(R.id.mainLoginBtn);

        //Creating new database
        db = new DatabaseHandler(this);
        //
        db.addUser(new User("Assem","Badr","Assem@gmail.com","12345"));
        db.addUser(new User("Yousef","Kmar","Yousef@gmail.com","123456"));
        db.addUser(new User("Abdullah","Halawa","Abdullah@gmail.com","1234567"));
        db.addUser(new User("Mohsen","Bl7","Mohsen@gmail.com","1122345"));

        User user = db.getUser("Assem@gmail.com");

        String myInfo = "ID: " + user.getId() + ", First name: " + user.getFirstName() +
                ", Last name: " + user.getLastName() + ", Email: " + user.getEmail() +
                ", Password" + user.getPassword();
        Log.d("People data", myInfo);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( getApplicationContext(),Registration.class);
                startActivity(myIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(),Login.class);
                startActivity(myIntent);
            }
        });

    }

}