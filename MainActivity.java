package com.example.appassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView emailr, passwordr;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        emailr = findViewById(R.id.emailr);
        passwordr = findViewById(R.id.passwordr);

        MaterialButton btnlog = findViewById(R.id.btnlogr);
        MaterialButton btnreg = findViewById(R.id.btnreg);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity2();
            }
        });
    }

    public void openMainActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void openHome() {
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void loginUser() {
        String email = emailr.getText().toString().trim();
        String Password = passwordr.getText().toString().trim();

        // Check if any field is empty
        if (email.isEmpty() || Password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
            UserModel user = UserModel.getUserFromDatabase(dbHelper, email);

            if (user != null && user.getPassword().equals(Password)) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                openHome();

            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
    }
}