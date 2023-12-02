package com.example.appassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView name,email,password;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        MaterialButton btnregr = (MaterialButton) findViewById(R.id.btnregr);
        MaterialButton btn = (MaterialButton) findViewById(R.id.btn);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        btnregr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }
    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    //Methode to process the registration
    private void registerUser() {
        String username = name.getText().toString();
        String emailr = email.getText().toString();
        String passwordr = password.getText().toString();


        // Check if any field is empty
        if (username.isEmpty() || emailr.isEmpty() || passwordr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check username length
        if (username.length() < 3 || username.length() > 10) {
            Toast.makeText(this, "Username must be between 3 and 10 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check email format
        if (!emailr.contains("@gmail.com")) {
            Toast.makeText(this, "Invalid email format. Must be a Gmail account", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check password complexity (contains digit and capital letter)
        if (!passwordContainsDigit(passwordr) || !passwordContainsCapitalLetter(passwordr)) {
            Toast.makeText(this, "Password must contain at least one digit and one capital letter", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        UserModel newUser = new UserModel(username, emailr, passwordr);

        editor.putString("Name", username);
        editor.putString("Email", emailr);
        editor.apply();

        // Add the new user to the UserManager
        UserModel.addUser(newUser);
        long newRowId = newUser.saveToDatabase(this);
        if (newRowId != -1) {
            // Registration successful
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            openProfil();
        } else {
            Toast.makeText(this, "Error during registration", Toast.LENGTH_SHORT).show();
        }

        openProfil();
    }
    public void openProfil() {
        Intent intent = new Intent(this, Profil.class);
        intent.putExtra("Name", name.getText().toString());
        intent.putExtra("Email", email.getText().toString());
        startActivity(intent);
    }
    private boolean passwordContainsDigit(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean passwordContainsCapitalLetter(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

}