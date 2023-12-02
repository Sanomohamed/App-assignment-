package com.example.appassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Profil extends AppCompatActivity {

    private TextView textView1,textView2,textView3,textView4;
    private EditText editTextText1,editTextText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        editTextText1=findViewById(R.id.editTextText1);
        editTextText=findViewById(R.id.editTextText);
        MaterialButton btnlog = findViewById(R.id.btnlogr);
        MaterialButton btnreg = findViewById(R.id.btnreg);
        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab1 = findViewById(R.id.fab1);

        String username = sharedPreferences.getString("Name", "");
        String storedEmail = sharedPreferences.getString("Email", "");
        String address = sharedPreferences.getString("Address", "");
        int contact = sharedPreferences.getInt("Contact", 0);

        textView1.setText(username);
        textView2.setText(storedEmail);
        textView3.setText(address);
        textView4.setText(String.valueOf(contact));

        Intent intent = getIntent();
        if (intent.hasExtra("Name") && intent.hasExtra("Email")) {
             username = intent.getStringExtra("Name");
            storedEmail = intent.getStringExtra("Email");

            // Save data to SharedPreferences when new data is received
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Name", username);
            editor.putString("Email", storedEmail);

            editor.putString("Address", "");
            editor.putInt("Contact", 0);

            editor.apply();

            textView1.setText(username);
            textView2.setText(storedEmail);
            textView3.setText("");
            textView4.setText("");
        }

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editTextText1.getText().toString();
                int contactString = Integer.parseInt(editTextText.getText().toString());

                if (address.isEmpty()) {
                    // Show a message if address or contact is empty
                    Toast.makeText(Profil.this, "Address or contact field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    int contact = Integer.parseInt(String.valueOf(contactString));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Address", address);
                    editor.putInt("Contact", contact);
                    editor.apply();

                    textView3.setText(address);
                    textView4.setText(String.valueOf(contact));

                    editTextText1.setText("");
                    editTextText.setText("");
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid integer
                    Toast.makeText(Profil.this, "Invalid contact number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();

            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notice")
                .setMessage("Update Your profil please\nFor help please Call our hotline\n12300021")
                .setPositiveButton("OK", null) // You can add listeners for positive, negative, and neutral buttons if needed
                .show();
    }
    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}